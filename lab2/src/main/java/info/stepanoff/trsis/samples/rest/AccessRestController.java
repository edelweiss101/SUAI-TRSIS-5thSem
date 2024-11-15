package info.stepanoff.trsis.samples.rest;

import info.stepanoff.trsis.samples.db.model.AccessID;
import info.stepanoff.trsis.samples.rest.model.AccessDTO;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/company/access")
public class AccessRestController {
    private final AccessService accessService;

    @Operation(summary = "Выдать доступ сотруднику",
            description = "Для выдачи доступа сотрудник и помещение должны существовать, иначе - 404",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "/{e_id}-{r_id}", method = RequestMethod.POST)
    public ResponseEntity<AccessDTO> create(
            @PathVariable("e_id")
            @Parameter(description = "ID сотрудника") Integer employeeID,
            @PathVariable("r_id")
            @Parameter(description = "ID помещения") Integer roomID){
        try {
            return new ResponseEntity<>(accessService.create(employeeID, roomID), HttpStatus.OK);
        }
        catch (Exception ex) {
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
    public ResponseEntity<List<AccessDTO>> read(){
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
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "/{e_id}-{r_id}", method = RequestMethod.GET)
    public ResponseEntity<AccessDTO> read(
            @PathVariable("e_id")
            @Parameter(description = "ID сотрудника") Integer employeeID,
            @PathVariable("r_id")
            @Parameter(description = "ID помещения") Integer roomID){
        final AccessDTO access = accessService.read(new AccessID(employeeID, roomID));
        return access != null
                ? new ResponseEntity<>(access, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Отозвать доступ у сотрудника",
            description = "Отзыв доступа к помещению у сотрудника",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "/{e_id}-{r_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(
            @PathVariable("e_id")
            @Parameter(description = "ID сотрудника") Integer employeeID,
            @PathVariable("r_id")
            @Parameter(description = "ID помещения") Integer roomID) {
        return accessService.delete(new AccessID(employeeID, roomID))
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
