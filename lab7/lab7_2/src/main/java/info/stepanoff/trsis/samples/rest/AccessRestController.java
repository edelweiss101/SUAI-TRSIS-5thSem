package info.stepanoff.trsis.samples.rest;

import info.stepanoff.trsis.samples.db.model.AccessID;
import info.stepanoff.trsis.samples.rest.exceptions.UnauthorizedException;
import info.stepanoff.trsis.samples.rest.model.AccessDTO;
import info.stepanoff.trsis.samples.service.KafkaProduceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.stepanoff.trsis.samples.service.AccessService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/company/access")
public class AccessRestController {
    private final AccessService accessService;
    private final KafkaProduceService kafkaProduceService;
    private static final String msg = "{\"op\": \"%s\", \"employee_id\": \"%d\", \"room_id\": \"%d\"}";

    @Operation(summary = "Выдать доступ сотруднику",
            description = "Для выдачи доступа сотрудник и помещение должны существовать, иначе - 404",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден"),
                    @ApiResponse(responseCode = "401",
                            description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403",
                            description = "Аутентификация предоставлена, но у пользователя нет доступа")
            })
    @RequestMapping(value = "/{e_id}-{r_id}", method = RequestMethod.POST)
    public ResponseEntity<AccessDTO> create(
            @Parameter(description = "ID сотрудника") @PathVariable("e_id") Integer employee_id,
            @Parameter(description = "ID помещения") @PathVariable("r_id") Integer room_id,
            Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException();
        }
        try {
            AccessDTO new_acc = accessService.create(employee_id, room_id);
            kafkaProduceService.send(msg.formatted("Access.create", employee_id, room_id));
            return new ResponseEntity<>(new_acc, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Отозвать доступ у сотрудника",
            description = "Отзыв доступа к помещению у сотрудника",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "401",
                            description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403",
                            description = "Аутентификация предоставлена, но у пользователя нет доступа"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "/{e_id}-{r_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(
            @Parameter(description = "ID сотрудника") @PathVariable("e_id") Integer employee_id,
            @Parameter(description = "ID помещения") @PathVariable("r_id") Integer room_id,
            Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException();
        }
        boolean found = accessService.delete(new AccessID(employee_id, room_id));
        if (found) {
            kafkaProduceService.send(msg.formatted("Access.delete", employee_id, room_id));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Получить список доступов",
            description = "Получение всех выданных сотрудникам доступов",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<AccessDTO>> read() {
        final List<AccessDTO> accesss = accessService.readAll();
        return accesss != null && !accesss.isEmpty()
                ? new ResponseEntity<>(accesss, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Проверить доступ сотрудника",
            description = "Проверка доступа сотрудника к помещению",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден"),
                    @ApiResponse(responseCode = "401",
                            description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403",
                            description = "Аутентификация предоставлена, но у пользователя нет доступа")
            })
    @RequestMapping(value = "/{e_id}-{r_id}", method = RequestMethod.GET)
    public ResponseEntity<AccessDTO> read(
            @Parameter(description = "ID сотрудника") @PathVariable("e_id") Integer employee_id,
            @Parameter(description = "ID помещения") @PathVariable("r_id") Integer room_id,
            Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException();
        }
        final AccessDTO access = accessService.read(new AccessID(employee_id, room_id));
        return access != null
                ? new ResponseEntity<>(access, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
