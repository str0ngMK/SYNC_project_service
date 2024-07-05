package project.service.kafka;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import project.service.kafka.event.CreateMemberAtProjectEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

}
