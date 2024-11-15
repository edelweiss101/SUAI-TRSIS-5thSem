package info.stepanoff.trsis.samples.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Доступ к помещению")
public class AccessDTO {
    @Schema(description = "ID сотрудника")
    private Integer employeeID;
    @Schema(description = "ID помещения")
    private Integer roomID;
}
