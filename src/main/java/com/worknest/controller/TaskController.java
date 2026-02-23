package com.worknest.controller;

import com.worknest.dto.TaskCreateDto;
import com.worknest.dto.TaskViewDto;
import com.worknest.entity.*;
import com.worknest.service.TaskService;
import com.worknest.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    // ================= VIEW TASKS =================
    @GetMapping("/tasks")
    public String tasks(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<TaskViewDto> tasks;

        if (user.getRole() == Role.ADMIN) {
            tasks = taskService.getAllTasks();
            model.addAttribute("title", "All Tasks");
        } else {
            tasks = taskService.getTasksForUser(user);
            model.addAttribute("title", "My Tasks");
        }

        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    // ================= ADD TASK =================
    @GetMapping("/task/add")
    public String addTask(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/login";
        }

        model.addAttribute("taskDto", new TaskCreateDto());
        model.addAttribute("users", userService.getAllEmployees());
        return "add_task";
    }

    // ================= EDIT TASK =================
    @GetMapping("/task/edit/{id}")
    public String editTask(@PathVariable Long id,
                           HttpSession session,
                           Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Task task = taskService.getTask(id);
        if (task == null) return "redirect:/tasks";

        TaskCreateDto dto = taskService.mapToDto(task);

        model.addAttribute("taskDto", dto);
        model.addAttribute("task", task);

        if (user.getRole() == Role.ADMIN
                || user.getRole() == Role.MANAGER) {
            model.addAttribute("users", userService.getAllEmployees());
        } else {
            model.addAttribute("statusOnly", true);
        }

        return "add_task";
    }

    // ================= SAVE TASK =================
    @PostMapping("/task/save")
    public String saveTask(@ModelAttribute TaskCreateDto dto,
                           HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (user.getRole() == Role.ADMIN
                || user.getRole() == Role.MANAGER) {
            taskService.createOrUpdate(dto);
        } else {
            taskService.updateStatusOnly(dto.getId(), TaskStatus.valueOf(dto.getStatus()), user);
        }

        return "redirect:/tasks";
    }

    // ================= DELETE TASK =================
    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole() == Role.ADMIN) {
            taskService.deleteTask(id);
        }

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/search")
    public String searchTasks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            HttpSession session,
            Model model
    ) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<TaskViewDto> tasks;

        if (user.getRole() == Role.ADMIN) {
            tasks = taskService.searchForAdmin(keyword, status, priority);
            model.addAttribute("title", "Search Results");
        } else {
            tasks = taskService.searchForUser(user, keyword, status, priority);
            model.addAttribute("title", "My Tasks");
        }

        model.addAttribute("tasks", tasks);

        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("priority", priority);

        return "tasks"; // ⚠️ SAME PAGE reuse
    }

}


