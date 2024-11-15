package info.stepanoff.trsis.samples.rest;

import info.stepanoff.trsis.samples.rest.model.EmployeeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.stepanoff.trsis.samples.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/company/employees")
public class EmployeeRestController {
    private final EmployeeService employeeService;

    @Operation(summary = "Добавить/изменить сотрудника",
            description = "Добавление/изменение сотрудника в зависимости от существования указанного ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "208",
                            description = "Успешное изменение")
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<EmployeeDTO> create(
            @PathVariable("id")
            @Parameter(description = "ID сотрудника") Integer id,
            @Parameter(description = "Имя") String name,
            @Parameter(description = "Отдел") String department,
            @Parameter(description = "Должность") String position){
        System.out.println("\n-----");
        System.out.println("id: " + id);
        System.out.println("name: " + name);
        System.out.println("department: " + department);
        System.out.println("position: " + position);
        System.out.println("-----\n");
        return employeeService.read(id) == null
                ? new ResponseEntity<EmployeeDTO>(employeeService.create(id, name, department, position), HttpStatus.OK)
                : new ResponseEntity<EmployeeDTO>(employeeService.create(id, name, department, position), HttpStatus.ALREADY_REPORTED);
    }

    @Operation(summary = "Получить список сотрудников",
            description = "Получение всех сотрудников и их данных",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDTO>> read(){
        final List<EmployeeDTO> employees = employeeService.readAll();
        return employees != null && !employees.isEmpty()
                ? new ResponseEntity<>(employees, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Получить данные сотрудника",
            description = "Получение данных сотрудника по его ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<EmployeeDTO> read(
            @PathVariable("id")
            @Parameter(description = "ID сотрудника") Integer id){
        final EmployeeDTO employee = employeeService.read(id);
        return employee != null
                ? new ResponseEntity<>(employee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Удалить сотрудника",
            description = "Удаление сотрудника по ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "404",
                            description = "Ресурс не найден")
            })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(
            @PathVariable("id")
            @Parameter(description = "ID сотрудника") Integer id) {
        return employeeService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
