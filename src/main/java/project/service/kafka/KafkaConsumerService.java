package project.service.kafka;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import project.service.ProjectService;
import project.service.dto.request.CreateProjectRequestDto;
import project.service.kafka.event.ProjectCreateEvent;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final ProjectService projectService;
    private static final String TOPIC = "project-create-topic";
    @KafkaListener(topics = TOPIC, groupId = "project_create_group", containerFactory = "kafkaListenerContainerFactory")
    public void listenProjectCreateEvent(ProjectCreateEvent event) {
        try {
            CreateProjectRequestDto projectCreateRequestDto = event.getProjectCreateRequestDto();
            String userId = event.getUserId();
            // 이벤트 처리
            projectService.createProject(projectCreateRequestDto, userId);
            // 처리 로그 출력
            log.info("Processed ProjectCreateEvent for userId: " + userId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}