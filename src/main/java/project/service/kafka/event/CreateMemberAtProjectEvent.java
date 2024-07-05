package project.service.kafka.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateMemberAtProjectEvent {
    Long projectId;
    String userId;
}
