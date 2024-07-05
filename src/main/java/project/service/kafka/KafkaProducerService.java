package project.service.kafka;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import project.service.kafka.event.CreateMemberAtProjectEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC1 = "member-create-at-project-topic";
    public void sendCreateMemberAtProjectEvent(String userId, Long projectId) {
        CreateMemberAtProjectEvent event = new CreateMemberAtProjectEvent(projectId, userId);
        kafkaTemplate.send(TOPIC1, event);
    }
}
