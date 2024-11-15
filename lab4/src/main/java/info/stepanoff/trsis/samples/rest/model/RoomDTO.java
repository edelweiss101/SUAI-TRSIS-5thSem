package info.stepanoff.trsis.samples.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Помещение")
public class RoomDTO {
    @Schema(description = "ID помещения")
    private Integer id;
    @Schema(description = "Название помещения")
    private String name;
    @Schema(description = "Отдел, которому принадлежит помещение")
    private String department;
    @Schema(description = "Вместительность помещения")
    private int capacity;
}
