package com.cyberforgetech.tasktracker.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.cyberforgetech.tasktracker.model.TaskStatus;

import java.time.LocalDateTime;

public record TaskCreateRequest(
        @NotBlank String title,
        String description,
        @NotNull TaskStatus status,
        LocalDateTime dueDate,
        Long assignedUserId
) {}