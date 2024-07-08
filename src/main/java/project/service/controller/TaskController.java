package project.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.service.TaskService;
import project.service.global.ResponseMessage;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    @GetMapping("/tasks/api/v1/getChildren")
    public ResponseMessage getOnlyChildrenTasks(Long taskId)  {
        return taskService.getOnlyChildrenTasks(taskId);
    }
}
