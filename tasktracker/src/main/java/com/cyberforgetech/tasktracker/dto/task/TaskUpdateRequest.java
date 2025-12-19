package com.cyberforgetech.tasktracker.dto.task;

import com.cyberforgetech.tasktracker.model.TaskStatus;
import java.time.LocalDateTime;

/**
 * DTO for partial update of a task (PUT request)
 * All fields are optional
 */
public record TaskUpdateRequest(
        String title,
        String description,
        TaskStatus status,
        LocalDateTime dueDate,
        Long assignedUserId
) {}