package com.cyberforgetech.tasktracker.scheduler;

import com.cyberforgetech.tasktracker.model.Task;
import com.cyberforgetech.tasktracker.model.TaskStatus;
import com.cyberforgetech.tasktracker.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OverdueTaskScheduler {

    private static final Logger log = LoggerFactory.getLogger(OverdueTaskScheduler.class);

    private final TaskRepository taskRepository;

    public OverdueTaskScheduler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Scheduled(cron = "${task.scheduler.cron:0 0 * * * ?}")  // hourly by default
    @Transactional
    public void markOverdueTasks() {
        LocalDateTime now = LocalDateTime.now();

        List<Task> overdueTasks = taskRepository.findAllByDueDateBeforeAndStatusNot(now, TaskStatus.COMPLETED);

        if (overdueTasks.isEmpty()) {
            log.info("Overdue task check: No overdue tasks found at {}", now);
            return;
        }

        overdueTasks.forEach(task -> {
            task.setStatus(TaskStatus.OVERDUE);
            log.info("Marked task '{}' as OVERDUE (was {})", task.getTitle(), task.getStatus());
        });

        taskRepository.saveAll(overdueTasks);

        log.info("Overdue task scheduler completed: {} tasks marked as OVERDUE", overdueTasks.size());
    }
}
