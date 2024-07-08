package project.service.kafka;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import project.service.ProjectService;
import project.service.TaskService;
import project.service.dto.request.CreateProjectRequestDto;
import project.service.dto.request.CreateTaskRequestDto;
import project.service.dto.request.MemberMappingToTaskRequestDto;
import project.service.kafka.event.ProjectCreateEvent;
import project.service.kafka.event.TaskCreateEvent;
import project.service.kafka.event.UserAddToTaskEvent;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private final ProjectService projectService;
    private final TaskService taskService;
    private static final String TOPIC = "project-create-topic";
    private static final String TOPIC1 = "task-create-topic";
    private static final String TOPIC2 = "task-add-user-topic";
    @KafkaListener(topics = TOPIC, groupId = "project_create_group", containerFactory = "kafkaProjectCreateEventListenerContainerFactory")
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
    @KafkaListener(topics = TOPIC1, groupId = "task-create-group", containerFactory = "kafkaTaskCreateEventListenerContainerFactory")
    public void listenTaskCreateEvent(TaskCreateEvent event) {
        try {
            CreateTaskRequestDto createTaskRequestDto = event.getCreateTaskRequestDto();
            // 이벤트 처리
            taskService.createTask(createTaskRequestDto);
            // 처리 로그 출력
            log.info("Processed TaskCreateEvent");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    @KafkaListener(topics = TOPIC2, groupId = "task-add-user-group", containerFactory = "kafkaAddUserToTaskEventListenerContainerFactory")
    public void listenAddUserToTaskEvent(UserAddToTaskEvent event) {
        try {
            // 이벤트 처리
            taskService.addUserToTask(event);
            // 처리 로그 출력
            log.info("Processed addUserToTaskEvent");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}