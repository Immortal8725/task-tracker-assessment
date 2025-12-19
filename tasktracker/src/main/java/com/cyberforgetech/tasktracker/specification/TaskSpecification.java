package com.cyberforgetech.tasktracker.specification;

import com.cyberforgetech.tasktracker.model.Task;
import com.cyberforgetech.tasktracker.model.TaskStatus;
import com.cyberforgetech.tasktracker.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskSpecification {

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Task> dueBefore(LocalDateTime dueBefore) {
        return (root, query, cb) -> dueBefore == null ? null : cb.lessThan(root.get("dueDate"), dueBefore);
    }

    public static Specification<Task> dueAfter(LocalDateTime dueAfter) {
        return (root, query, cb) -> dueAfter == null ? null : cb.greaterThan(root.get("dueDate"), dueAfter);
    }

    public static Specification<Task> assignedTo(Long assigneeId) {
        return (root, query, cb) -> assigneeId == null ? null : cb.equal(root.get("assignedUser").get("id"), assigneeId);
    }
}