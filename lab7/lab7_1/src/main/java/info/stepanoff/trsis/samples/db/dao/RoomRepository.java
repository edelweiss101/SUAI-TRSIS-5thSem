package info.stepanoff.trsis.samples.db.dao;

import info.stepanoff.trsis.samples.db.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    public List<Room> findAll();
}
