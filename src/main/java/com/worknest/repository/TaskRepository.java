package com.worknest.repository;

import com.worknest.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // ================= DASHBOARD =================
    long countByUser_Role(Role role);

    long countByStatusAndUser_Role(TaskStatus status, Role role);

    // ================= BASIC FETCH =================
    List<Task> findByUser(User user);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    // ================= USER FILTERS =================
    List<Task> findByUserAndStatus(User user, TaskStatus status);

    List<Task> findByUserAndPriority(User user, TaskPriority priority);

    List<Task> findByUserAndTitleContainingIgnoreCase(User user, String title);

    // ================= ADMIN SEARCH =================
    List<Task> findByTitleContainingIgnoreCase(String keyword);

    // (optional – future use)
    // List<Task> findAllByOrderByCreatedAtDesc();
}
