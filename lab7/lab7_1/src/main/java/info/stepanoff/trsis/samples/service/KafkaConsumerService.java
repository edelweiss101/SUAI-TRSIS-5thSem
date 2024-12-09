package info.stepanoff.trsis.samples.service;

import info.stepanoff.trsis.samples.db.model.AccessID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final EmployeeService employeeService;
    private final RoomService roomService;
    private final AccessService accessService;

    @KafkaListener(groupId = "${kafka.group.id}", topics = "${kafka.consumeTopic}")
    public void consume(String message) {
        log.info("==> consumed {}", message);
        JSONObject jsonMessage = new JSONObject(message);
        String op = jsonMessage.getString("op");
        switch (op) {
            case "Employee.create":
                employeeService.create(
                    Integer.parseInt(jsonMessage.getString("id")),
                    jsonMessage.getString("name"),
                    jsonMessage.getString("department"),
                    jsonMessage.getString("position")
                );
                break;
            case "Employee.delete":
                employeeService.delete(Integer.parseInt(jsonMessage.getString("id")));
                break;
            case "Room.create":
                roomService.create(
                    Integer.parseInt(jsonMessage.getString("id")),
                    jsonMessage.getString("name"),
                    jsonMessage.getString("department"),
                    Integer.parseInt(jsonMessage.getString("capacity"))
                );
                break;
            case "Room.delete":
                roomService.delete(Integer.parseInt(jsonMessage.getString("id")));
                break;
            case "Access.create":
                accessService.create(
                    Integer.parseInt(jsonMessage.getString("employee_id")),
                    Integer.parseInt(jsonMessage.getString("room_id"))
                );
                break;
            case "Access.delete":
                accessService.delete(new AccessID(
                    Integer.parseInt(jsonMessage.getString("employee_id")),
                    Integer.parseInt(jsonMessage.getString("room_id"))
                ));
                break;
            default:
                log.info("=> consumed unknown operation: {}", op);
        }
    }
}
