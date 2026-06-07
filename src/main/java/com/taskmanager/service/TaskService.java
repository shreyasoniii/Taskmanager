package com.taskmanager.service;

import com.taskmanager.dto.request.TaskRequest;
import com.taskmanager.dto.response.TaskResponse;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.Task.Status;
import com.taskmanager.entity.User;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // ── Helper: get logged-in user ────────────────────────
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // ── Create ────────────────────────────────────────────
    public TaskResponse createTask(TaskRequest request) {
        User user = getCurrentUser();

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .status(request.getStatus())
                .user(user)
                .build();

        return TaskResponse.from(taskRepository.save(task));
    }

    // ── Read all (optionally filtered by status) ──────────
    public List<TaskResponse> getAllTasks(Status status) {
        User user = getCurrentUser();

        List<Task> tasks = (status != null)
                ? taskRepository.findByUserIdAndStatusOrderByCreatedAtDesc(user.getId(), status)
                : taskRepository.findByUserIdOrderByCreatedAtDesc(user.getId());

        return tasks.stream()
                .map(TaskResponse::from)
                .collect(Collectors.toList());
    }

    // ── Read single ───────────────────────────────────────
    public TaskResponse getTaskById(Long taskId) {
        User user = getCurrentUser();
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        return TaskResponse.from(task);
    }

    // ── Update ────────────────────────────────────────────
    public TaskResponse updateTask(Long taskId, TaskRequest request) {
        User user = getCurrentUser();
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setStatus(request.getStatus());

        return TaskResponse.from(taskRepository.save(task));
    }

    // ── Delete ────────────────────────────────────────────
    public void deleteTask(Long taskId) {
        User user = getCurrentUser();
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        taskRepository.delete(task);
    }

    // ── Update status only ────────────────────────────────
    public TaskResponse updateStatus(Long taskId, Status status) {
        User user = getCurrentUser();
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        task.setStatus(status);
        return TaskResponse.from(taskRepository.save(task));
    }
}
