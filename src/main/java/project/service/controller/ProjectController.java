package project.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.service.ProjectService;
import project.service.global.ResponseMessage;

@RestController
@RequestMapping("/project/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
    final ProjectService projectService;
    @GetMapping("/find")
    public ResponseMessage findProject(Long projectId)  {
        return projectService.findProject(projectId);
    }
}
