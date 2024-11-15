package info.stepanoff.trsis.samples.db.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessID implements Serializable {
    private static final long serialVersionUID = 4L;

    private Integer employeeID;
    private Integer roomID;
}
