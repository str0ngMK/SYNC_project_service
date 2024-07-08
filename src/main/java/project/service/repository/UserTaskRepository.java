package project.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.service.entity.UserTask;
import project.service.entity.UserTaskId;
@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId>{
}
