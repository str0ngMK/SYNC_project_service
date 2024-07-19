package project.service.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.service.dto.request.UpdateTaskRequestDto;

@AllArgsConstructor
@Getter
public class TaskUpdateEvent {
    UpdateTaskRequestDto updateTaskRequestDto;
}
