package com.cyberforgetech.tasktracker.dto.task;

import com.cyberforgetech.tasktracker.model.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime dueDate,
        LocalDateTime createdDate,
        Long assignedUserId,
        String assignedUserUsername
) {}