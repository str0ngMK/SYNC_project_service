package project.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.service.dto.request.CreateProjectRequestDto;
import project.service.entity.Project;
import project.service.global.ResponseMessage;
import project.service.kafka.KafkaProducerService;
import project.service.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final KafkaProducerService kafkaProducerService;
	@Transactional(rollbackFor = { Exception.class })
	public void createProject(CreateProjectRequestDto projectCreateRequestDto, String userId) {
		Project project = Project.builder()
				.description(projectCreateRequestDto.getDescription())
				.subTitle(projectCreateRequestDto.getSubTitle())
				.startDate(projectCreateRequestDto.getStartDate())
				.endDate(projectCreateRequestDto.getEndDate())
				.title(projectCreateRequestDto.getTitle()).build();
		projectRepository.save(project);
//		MemberMappingToProjectRequestDto memberMappingToProjectRequestDto = MemberMappingToProjectRequestDto.builder()
//				.projectId(project.getId()).userId(userService.getCurrentUserId()).isManager(true).build();
//		memberService.memberAddToProject(memberMappingToProjectRequestDto);
//
//		// elasticsearch index 추가
//		searchService.newInit(project);
//
//		// 초대 URL 생성
//		inviteService.createLink(project);
	}
	@Transactional(rollbackFor = { Exception.class })
	public ResponseMessage findProject(Long projectId) {
		try {
			Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(projectId)));
			// 프로젝트를 찾았을 때의 로직을 여기에 추가하세요.
			// 예를 들어, 성공 메시지를 반환할 수 있습니다.
			return new ResponseMessage("프로젝트 조회 성공", true, project.getId());
		} catch (EntityNotFoundException e) {
			return new ResponseMessage("해당 프로젝트는 존재하지 않습니다.", false, e.getMessage());
		}
	}
//	@Transactional(rollbackFor = { Exception.class })
//	public ResponseMessage getProjects(GetProjectsRequestDto getProjectsRequestDto) {
//		List<GetProjectsResponseDto> result;
//		try {
//			User user = userRepository.findByAuthenticationUserId(getProjectsRequestDto.getUserId());
//			List<Project> projects = memberRepository.findProjectsByUserId(user.getId());
//			result = projects.stream()
//					.map(project -> new GetProjectsResponseDto(project.getId(), project.getTitle(),
//							project.getDescription(), project.getStartDate(), project.getEndDate(), project.getMembers()
//									.stream().map(member -> member.getUser().getId()).collect(Collectors.toList())))
//					.collect(Collectors.toList());
//		} catch (NullPointerException e) {
//			throw new UserNotFoundException(e.getMessage());
//		}
//		return ResponseMessage.builder().value(result).build();
//	}
//
//	/*
//	 * new
//	 */
//	@Transactional(rollbackFor = { Exception.class })
//	public ResponseMessage getProjects(String userId) {
//		List<GetProjectsResponseDto> result;
//		try {
//			User user = userRepository.findByAuthenticationUserId(userId);
//			List<Project> projects = memberRepository.findProjectsByUserId(user.getId());
//			result = projects.stream()
//					.map(project -> new GetProjectsResponseDto(project.getId(), project.getTitle(),
//							project.getDescription(), project.getStartDate(), project.getEndDate(), project.getMembers()
//								.stream().map(member -> member.getUser().getId()).collect(Collectors.toList())))
//					.collect(Collectors.toList());
//		} catch (NullPointerException e) {
//			throw new UserNotFoundException(e.getMessage());
//		}
//		return ResponseMessage.builder()
//				.value(result)
//				.message("프로젝트 조회 성공")
//				.build();
//	}
//
//	@Transactional(rollbackFor = { Exception.class })
//	public ResponseMessage deleteProject(DeleteProjectRequestDto projectDeleteRequestDto) {
//		try {
//			Optional<Project> opProject = projectRepository.findById(projectDeleteRequestDto.getProjectId());
//			User user = userRepository.findByAuthenticationUserId(userService.getCurrentUserId());
//			if (opProject.isPresent()) {
//				Project project = opProject.get();
//				project.getTasks().size();
//				isProjectManager(user, project);
//
////				// 연관된 task 삭제
////				List<Task> tasks = project.getTasks();
////				for (Task task : tasks) {
////					task.getTaskMembers().clear();
////					userTaskRepository.deleteByTaskId(task.getId());
////					taskRepository.delete(task);
////				}
////				// 연관된 member 삭제
////				List<Member> members = project.getMembers();
////				for (Member member : members) {
////					member.getTaskMembers().clear();
////					memberRepository.delete(member);
////				}
//				projectRepository.delete(project);
//			} else {
//				throw new EntityNotFoundException(
//						"해당 프로젝트는 존재하지 않습니다. ProjectId : " + projectDeleteRequestDto.getProjectId());
//			}
//		} catch (NullPointerException e) {
//			throw new UserNotFoundException(e.getMessage());
//		}
//		return ResponseMessage.builder().message("success").build();
//	}
//
//	// 프로젝트의 관리자인지 확인합니다.
//	public void isProjectManager(User user, Project project) {
//		Long userId = user.getId();
//
//		Optional<Member> member = memberRepository.findByUserIdAndProjectId(userId, project.getId());
//
//		if (member.isPresent()) {
//			if (!member.get().isManager()) {
//				throw new InvalidValueException("해당 멤버는 해당 프로젝트의 관리자가 아닙니다. ProjectId : " + project.getId()
//						+ " UserId : " + user.getAuthentication().getUserId());
//			}
//		} else {
//			throw new InvalidValueException("해당 멤버는 해당 프로젝트에 소속되어 있지 않습니다. ProjectId : " + project.getId()
//					+ " UserId : " + user.getAuthentication().getUserId());
//		}
//	}
//	public void isProjectMember(User user, Project project) {
//		Long userId = user.getId();
//
//		Optional<Member> member = memberRepository.findByUserIdAndProjectId(userId, project.getId());
//
//		if (member.isEmpty()) {
//			// 유효한 멤버
//			throw new InvalidValueException("해당 멤버는 해당 프로젝트에 소속되어 있지 않습니다. ProjectId : " + project.getId()
//					+ " UserId : " + user.getAuthentication().getUserId());
//		}
//	}
}
