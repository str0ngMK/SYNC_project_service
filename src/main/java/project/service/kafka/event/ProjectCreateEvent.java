package project.service.kafka.event;

import lombok.Getter;
import lombok.Setter;
import project.service.dto.request.CreateProjectRequestDto;

@Getter
@Setter
public class ProjectCreateEvent {
    private CreateProjectRequestDto projectCreateRequestDto;
    private String userId;

}
