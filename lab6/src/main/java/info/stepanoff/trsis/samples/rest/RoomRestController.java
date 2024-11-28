package info.stepanoff.trsis.samples.rest;

import info.stepanoff.trsis.samples.rest.exceptions.UnauthorizedException;
import info.stepanoff.trsis.samples.rest.model.RoomDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import info.stepanoff.trsis.samples.service.RoomService;

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
@RequestMapping("/company/rooms")
public class RoomRestController {
    private final RoomService roomService;

    @Operation(summary = "Добавить/изменить помещение",
            description = "Добавление/изменение помещения в зависимости от существования указанного ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "208",
                            description = "Успешное изменение"),
                    @ApiResponse(responseCode = "401",
                            description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403",
                            description = "Аутентификация предоставлена, но у пользователя нет доступа")
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<RoomDTO> create(
            @Parameter(description = "ID помещения") @PathVariable("id") Integer id,
            @Parameter(description = "Название") @RequestParam("name") String name,
            @Parameter(description = "Отдел") @RequestParam("department") String department,
            @Parameter(description = "Вместительность") @RequestParam("capacity") Integer capacity,
            Principal principal) {
        System.out.println("\n-----");
        System.out.println("id: " + id);
        System.out.println("name: " + name);
        System.out.println("department: " + department);
        System.out.println("capacity: " + capacity);
        System.out.println("-----\n");
        if (principal == null) {
            throw new UnauthorizedException();
        }
        return roomService.read(id) == null
                ? new ResponseEntity<RoomDTO>(roomService.create(id, name, department, capacity), HttpStatus.OK)
                : new ResponseEntity<RoomDTO>(roomService.create(id, name, department, capacity), HttpStatus.ALREADY_REPORTED);
    }

    @Operation(summary = "Получить список помещений",
            description = "Получение всех помещений методом GET",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<RoomDTO>> read() {
        final List<RoomDTO> rooms = roomService.readAll();
        return rooms != null && !rooms.isEmpty()
                ? new ResponseEntity<>(rooms, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получить список помещений",
            description = "Получение всех помещений методом POST",
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<List<RoomDTO>> readPost(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException();
        }
        final List<RoomDTO> rooms = roomService.readAll();
        return rooms != null && !rooms.isEmpty()
                ? new ResponseEntity<>(rooms, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получить данные помещения",
            description = "Получение данных помещения по его ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoomDTO> read(
            @Parameter(description = "ID помещения") @PathVariable("id") Integer id) {
        final RoomDTO room = roomService.read(id);
        return room != null
                ? new ResponseEntity<>(room, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Удалить помещение",
            description = "Удаление помещения по ID",
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(
            @Parameter(description = "ID помещения") @PathVariable("id") Integer id,
            Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException();
        }
        return roomService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
