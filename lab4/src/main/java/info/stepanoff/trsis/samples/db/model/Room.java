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
@Table(name = "ROOM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Serializable {
    private static final long serialVersionUID = 2L;

    public Room(Integer id, String name, String department, Integer capacity){
        this.id = id;
        this.name = name;
        this.department = department;
        this.capacity = capacity;
    }

    @Id
    @Column(name = "ROOM_ID")
    private Integer id;

    @OneToMany(targetEntity = Access.class, mappedBy = "roomID", fetch = FetchType.LAZY)
    List<Access> access;

    @Column(name = "ROOM_NAME")
    private String name;

    @Column(name = "ROOM_DEPARTMENT")
    private String department;

    @Column(name = "ROOM_CAPACITY")
    private Integer capacity;
}
