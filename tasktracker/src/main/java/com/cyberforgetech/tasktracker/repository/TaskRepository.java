package com.cyberforgetech.tasktracker.repository;

import com.cyberforgetech.tasktracker.model.Task;
import com.cyberforgetech.tasktracker.model.TaskStatus;
import com.cyberforgetech.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    List<Task> findAllByDueDateBeforeAndStatusNot(LocalDateTime dueDate, TaskStatus status);
}
