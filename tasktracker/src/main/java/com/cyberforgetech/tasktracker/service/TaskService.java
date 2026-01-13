package com.cyberforgetech.tasktracker.service;

import com.cyberforgetech.tasktracker.dto.task.TaskCreateRequest;
import com.cyberforgetech.tasktracker.dto.task.TaskResponse;
import com.cyberforgetech.tasktracker.dto.task.TaskUpdateRequest;
import com.cyberforgetech.tasktracker.model.Task;
import com.cyberforgetech.tasktracker.model.TaskStatus;
import com.cyberforgetech.tasktracker.model.User;
import com.cyberforgetech.tasktracker.repository.TaskRepository;
import com.cyberforgetech.tasktracker.repository.UserRepository;
import com.cyberforgetech.tasktracker.specification.TaskSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponse> getAllTasks(TaskStatus status, LocalDateTime dueBefore, LocalDateTime dueAfter, Long assigneeId) {
        Specification<Task> spec = Specification.where(TaskSpecification.hasStatus(status))
                .and(TaskSpecification.dueBefore(dueBefore))
                .and(TaskSpecification.dueAfter(dueAfter))
                .and(TaskSpecification.assignedTo(assigneeId));

        return taskRepository.findAll(spec).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return mapToResponse(task);
    }

    public TaskResponse createTask(TaskCreateRequest request) {
        User assignee = request.assignedUserId() == null ? null : userRepository.findById(request.assignedUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .dueDate(request.dueDate())
                .assignedUser(assignee)
                .build();

        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (request.title() != null) task.setTitle(request.title());
        if (request.description() != null) task.setDescription(request.description());
        if (request.status() != null) task.setStatus(request.status());
        if (request.dueDate() != null) task.setDueDate(request.dueDate());
        if (request.assignedUserId() != null) {
            User assignee = userRepository.findById(request.assignedUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            task.setAssignedUser(assignee);
        }

        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    private TaskResponse mapToResponse(Task task) {
        String username = task.getAssignedUser() != null ? task.getAssignedUser().getUsername() : null;
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getCreatedDate(),
                task.getAssignedUser() != null ? task.getAssignedUser().getId() : null,
                username
        );
    }
}