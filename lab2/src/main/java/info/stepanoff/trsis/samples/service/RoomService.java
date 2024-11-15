package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.rest.model.RoomDTO;
import java.util.List;

public interface RoomService {
    RoomDTO create(Integer id, String name, String department, Integer capacity);
    List<RoomDTO> readAll();
    RoomDTO read(Integer id);
    boolean delete(Integer id);
}
