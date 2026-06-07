package com.taskmanager.dto.response;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.Task.Priority;
import com.taskmanager.entity.Task.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor    // FIX: Added — required by Jackson
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDate dueDate;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TaskResponse from(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
