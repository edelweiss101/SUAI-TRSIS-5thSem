package info.stepanoff.trsis.samples.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Помещение")
public class RoomDTO extends AbstractDTO {
    @Schema(description = "ID помещения")
    private Integer id;
    @Schema(description = "Название помещения")
    private String name;
    @Schema(description = "Отдел, которому принадлежит помещение")
    private String department;
    @Schema(description = "Вместительность помещения")
    private int capacity;
}
