package info.stepanoff.trsis.samples.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сотрудник")
public class EmployeeDTO {
    @Schema(description = "ID сотрудника")
    private Integer id;
    @Schema(description = "Имя сотрудника")
    private String name;
    @Schema(description = "Отдел, где работает сотрудник")
    private String department;
    @Schema(description = "Должность сотрудника")
    private String position;
}
