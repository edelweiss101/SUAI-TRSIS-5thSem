package info.stepanoff.trsis.samples.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProduceService {
    @Autowired
    private KafkaTemplate<Long, String> kafkaTemplate;
    @Value("${kafka.produceTopic}")
    private String topic;

    @Async
    public void send(String message) {
        log.info("<== sending {}", message);
        kafkaTemplate.send(topic, message);
    }
}
