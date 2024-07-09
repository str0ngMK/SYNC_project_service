package project.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.service.dto.request.CreateProjectRequestDto;
import project.service.dto.request.GetProjectsRequestDto;
import project.service.dto.request.UpdateProjectRequestDto;
import project.service.dto.response.GetProjectsResponseDto;
import project.service.entity.Project;
import project.service.global.ResponseMessage;
import project.service.kafka.event.ProjectDeleteEvent;
import project.service.kafka.event.ProjectUpdateEvent;
import project.service.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final ProjectRepository projectRepository;
	@Transactional(rollbackFor = { Exception.class })
	public void createProject(CreateProjectRequestDto projectCreateRequestDto) {
		Project project = Project.builder()
				.description(projectCreateRequestDto.getDescription())
				.subTitle(projectCreateRequestDto.getSubTitle())
				.startDate(projectCreateRequestDto.getStartDate())
				.endDate(projectCreateRequestDto.getEndDate())
				.title(projectCreateRequestDto.getTitle()).build();
		projectRepository.save(project);
	}
	@Transactional(rollbackFor = { Exception.class })
	public ResponseMessage findProject(Long projectId) {
		try {
			Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(projectId)));

			return new ResponseMessage("프로젝트 조회 성공", true, project.getId());
		} catch (EntityNotFoundException e) {
			return new ResponseMessage("해당 프로젝트는 존재하지 않습니다.", false, e.getMessage());
		}
	}
	@Transactional(rollbackFor = { Exception.class })
    public void deleteProject(ProjectDeleteEvent event) {
		Optional<Project> project = projectRepository.findById(event.getProjectId());
		//프로젝트가 존재하지 않을 경우 에러 처리 로직 추가
		projectRepository.delete(project.get());
    }
	@Transactional(rollbackFor = { Exception.class })
	public List<GetProjectsResponseDto> getProjects(GetProjectsRequestDto getProjectsRequestDto) {
		List<GetProjectsResponseDto> result = getProjectsRequestDto.getProjectIds().stream()
				.map(projectRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(project -> new GetProjectsResponseDto(
						project.getId(),
						project.getTitle(),
						project.getDescription(),
						project.getStartDate(),
						project.getEndDate()))
				.collect(Collectors.toList());
		return result;
	}
	@Transactional(rollbackFor = { Exception.class })
	public void updateProject(ProjectUpdateEvent event) {
		UpdateProjectRequestDto updateProjectRequestDto = event.getProjectUpdateRequestDto();
		Optional<Project> project = projectRepository.findById(updateProjectRequestDto.getProjectId());
		Project getProject = project.get();
		getProject.setDescription(updateProjectRequestDto.getDescription());
		getProject.setSubTitle(updateProjectRequestDto.getSubTitle());
		getProject.setStartDate(updateProjectRequestDto.getStartDate());
		getProject.setEndDate(updateProjectRequestDto.getEndDate());
		getProject.setTitle(updateProjectRequestDto.getTitle());
		projectRepository.save(getProject);
	}
}
