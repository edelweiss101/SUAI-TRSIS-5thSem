package info.stepanoff.trsis.samples.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "EMPLOYEE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    public Employee(Integer id, String name, String department, String position){
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
    }

    @Id
    @Column(name = "EMPLOYEE_ID")
    private Integer id;

    @OneToMany(targetEntity = Access.class, mappedBy = "employeeID", fetch = FetchType.LAZY)
    List<Access> access;

    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @Column(name = "EMPLOYEE_DEPARTMENT")
    private String department;

    @Column(name = "EMPLOYEE_POSITION")
    private String position;
}
