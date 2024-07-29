package project.service.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
public class RollbackMemberAddToProjectEvent {

    Long projectId;
    List<String> userIds;
}
