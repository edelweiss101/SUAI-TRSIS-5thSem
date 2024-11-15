package info.stepanoff.trsis.samples.db.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import info.stepanoff.trsis.samples.db.model.Room;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    public List<Room> findAll();
}
