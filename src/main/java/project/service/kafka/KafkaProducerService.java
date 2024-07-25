package project.service.kafka;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import project.service.kafka.event.ProjectCreateEvent;
import project.service.kafka.event.UserAddToProjectEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "member-add-to-project-topic";

    public void sendAddMemberToProjectEvent(String userId, Long projectId) {
        UserAddToProjectEvent event = new UserAddToProjectEvent(projectId, userId);
        ProducerRecord<String, Object> record = new ProducerRecord<>(TOPIC, event);
        record.headers().remove("spring.json.header.types");
        kafkaTemplate.send(record);
    }
}
