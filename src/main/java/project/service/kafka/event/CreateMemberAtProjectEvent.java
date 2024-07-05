package project.service.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateMemberAtProjectEvent {
    Long projectId;
    String userId;
}
