package info.stepanoff.trsis.samples.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "ACCESS")
@IdClass(AccessID.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Access implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @Column(name = "ACC_EMPLOYEE")
    private Integer employeeID;

    @Id
    @Column(name = "ACC_ROOM")
    private Integer roomID;
}
