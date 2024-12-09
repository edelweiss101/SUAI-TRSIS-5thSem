package info.stepanoff.trsis.samples.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.stepanoff.trsis.samples.db.dao.RoomRepository;
import info.stepanoff.trsis.samples.db.model.Room;
import info.stepanoff.trsis.samples.rest.model.RoomDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<Long, String> kafkaRoomTemplate;

    @Override
    public RoomDTO create(Integer id, String name, String department, Integer capacity){
        return objectMapper.convertValue(roomRepository.save(new Room(id, name, department, capacity)), RoomDTO.class);
    }

    @Override
    public List<RoomDTO> readAll(){
        return roomRepository.findAll().stream()
                .map(room -> objectMapper.convertValue(room, RoomDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO read(Integer id){
        return objectMapper.convertValue(roomRepository.findById(id), RoomDTO.class);
    }

    @Override
    public boolean delete(Integer id){
        if (!roomRepository.existsById(id))
            return false;
        roomRepository.deleteById(id);
        return true;
    }
}
