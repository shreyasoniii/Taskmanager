package com.taskmanager.repository;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.Task.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // All tasks for a user
    List<Task> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Tasks filtered by status
    List<Task> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, Status status);

    // Single task belonging to user (prevents accessing other users' tasks)
    Optional<Task> findByIdAndUserId(Long taskId, Long userId);
}
