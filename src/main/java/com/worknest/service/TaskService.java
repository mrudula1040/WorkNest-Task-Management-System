package com.worknest.service;

import com.worknest.dto.TaskCreateDto;
import com.worknest.dto.TaskViewDto;
import com.worknest.entity.*;
import com.worknest.mapper.TaskMapper;
import com.worknest.repository.TaskRepository;
import com.worknest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    // ================= CREATE / UPDATE =================
    public void createOrUpdate(TaskCreateDto dto) {

        Task task;

        if (dto.getId() != null) {
            task = taskRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Task not found"));
            taskMapper.updateEntityFromDto(dto, task);
        } else {
            task = taskMapper.toEntity(dto);
            task.setCreatedAt(LocalDate.now());
        }

        if (task.getPriority()==null) {
            task.setPriority(TaskPriority.MEDIUM);
        }

        if (dto.getAssignedUserId() != null) {
            User user = userRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            task.setUser(user);
        }

        taskRepository.save(task);
    }

    // ================= USER =================
    public List<TaskViewDto> getTasksForUser(User user) {
        return taskRepository.findByUser(user)
                .stream()
                .map(taskMapper::toViewDto)
                .toList();
    }

    public void updateStatusOnly(Long taskId, TaskStatus status, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        task.setStatus(status);
        taskRepository.save(task);
    }

    // ================= ADMIN =================
    public List<TaskViewDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toViewDto)
                .toList();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // ================= SEARCH =================
    public List<TaskViewDto> searchForUser(User user,
                                           String keyword,
                                           TaskStatus status,
                                           TaskPriority priority) {

        return taskRepository.findByUser(user)
                .stream()
                .filter(t -> keyword == null || keyword.isBlank()
                        || t.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .filter(t -> status == null || t.getStatus()== status)
                .filter(t -> priority == null || t.getPriority()== priority)
                .map(taskMapper::toViewDto)
                .toList();
    }

    public List<TaskViewDto> searchForAdmin(String keyword,
                                            TaskStatus status,
                                            TaskPriority priority) {

        return taskRepository.findAll()
                .stream()
                .filter(t -> keyword == null || keyword.isBlank()
                        || t.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .filter(t -> status == null|| t.getStatus()== status)
                .filter(t -> priority == null || t.getPriority()== priority)
                .map(taskMapper::toViewDto)
                .toList();
    }

    // ================= DASHBOARD =================
    public long getTotalTasksCount() {
        return taskRepository.countByUser_Role(Role.USER);
    }

    public long getCountByStatusForUsers(TaskStatus status) {
        return taskRepository.countByStatusAndUser_Role(status, Role.USER);
    }

    public long getOverdueCount() {
        return taskRepository.findAll()
                .stream()
                .filter(t -> t.getDueDate() != null
                        && t.getDueDate().isBefore(LocalDate.now()))
                .filter(t -> t.getStatus()!=TaskStatus.DONE)
                .count();
    }

    // ================= UTIL =================
    public TaskCreateDto mapToDto(Task task) {
        return taskMapper.toCreateDto(task);
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
}
