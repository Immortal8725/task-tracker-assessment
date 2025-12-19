package com.cyberforgetech.tasktracker.repository;

import com.cyberforgetech.tasktracker.model.Task;
import com.cyberforgetech.tasktracker.model.TaskStatus;
import com.cyberforgetech.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
}