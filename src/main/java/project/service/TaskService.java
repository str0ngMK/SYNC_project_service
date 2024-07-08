package project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.service.dto.request.CreateTaskRequestDto;
import project.service.dto.response.GetTaskResponseDto;
import project.service.entity.Project;
import project.service.entity.Task;
import project.service.entity.UserTask;
import project.service.entity.UserTaskId;
import project.service.global.ResponseMessage;
import project.service.kafka.event.UserAddToTaskEvent;
import project.service.repository.ProjectRepository;
import project.service.repository.TaskRepository;
import project.service.repository.UserTaskRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserTaskRepository userTaskRepository;
    @Transactional(rollbackFor = { Exception.class })
    public void createTask(CreateTaskRequestDto createTaskRequestDto) {
        Optional<Project> project = projectRepository.findById(createTaskRequestDto.getProjectId());
        //project id 존재하지 않는경우 예외처리 해야함 (추가)
        Optional<Task> parentTask = taskRepository.findById(createTaskRequestDto.getParentTaskId());
        Task task;
        if (parentTask.isPresent()) {
            task = Task.builder().title(createTaskRequestDto.getTitle())
                    .description(createTaskRequestDto.getDescription()).parentTask(parentTask.get())
                    .endDate(createTaskRequestDto.getEndDate()).startDate(createTaskRequestDto.getStartDate())
                    .status(createTaskRequestDto.getStatus()).project(project.get()).build();
        } else {
            task = Task.builder().title(createTaskRequestDto.getTitle())
                    .description(createTaskRequestDto.getDescription()).endDate(createTaskRequestDto.getEndDate())
                    .startDate(createTaskRequestDto.getStartDate()).status(createTaskRequestDto.getStatus())
                    .project(project.get()).build();
        }
        taskRepository.save(task);
    }
    @Transactional(rollbackFor = { Exception.class })
    public ResponseMessage getOnlyChildrenTasks(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            return ResponseMessage.builder()
                    .result(false)
                    .build();
        }else{
            Task task = taskOptional.get();
            GetTaskResponseDto result = GetTaskResponseDto.fromEntityOnlyChildrenTasks(task);
            return ResponseMessage.builder()
                    .result(true)
                    .value(result).build();
        }

    }
    @Transactional(rollbackFor = { Exception.class })
    public void addUserToTask(UserAddToTaskEvent userAddToTaskEvent) {
        Optional<Task> task = taskRepository.findById(userAddToTaskEvent.getTaskId());
        //task id 존재하지 않는경우 예외처리 해야함 (추가)
        List<Long> userIds = userAddToTaskEvent.getUserIds();
        userIds.stream().forEach(userId -> {
            UserTaskId userTaskId = UserTaskId.builder()
                    .userId(userId)
                    .taskId(task.get().getId())
                    .build();
            UserTask userTask = UserTask.builder()
                    .task(task.get())
                    .id(userTaskId).build();
            userTaskRepository.save(userTask);
        });
    }
}