package project.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;
import project.service.dto.request.CreateTaskRequestDto;
import project.service.entity.Project;
import project.service.entity.Task;
import project.service.global.ResponseMessage;
import project.service.repository.ProjectRepository;
import project.service.repository.TaskRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    @Transactional(rollbackFor = { Exception.class })
    public void createTask(CreateTaskRequestDto createTaskRequestDto) {
        Optional<Project> project = projectRepository.findById(createTaskRequestDto.getProjectId());
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
}