package info.stepanoff.trsis.samples.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Сотрудник")
public class EmployeeDTO extends AbstractDTO {
    @Schema(description = "ID сотрудника")
    private Integer id;
    @Schema(description = "Имя сотрудника")
    private String name;
    @Schema(description = "Отдел, где работает сотрудник")
    private String department;
    @Schema(description = "Должность сотрудника")
    private String position;
}
