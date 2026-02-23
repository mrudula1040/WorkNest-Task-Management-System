package com.worknest.controller;


import com.worknest.entity.Role;
import com.worknest.entity.User;
import com.worknest.service.TaskService;
import com.worknest.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/admin/user/{id}/tasks")
    public String viewUserProfile(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        User admin = (User) session.getAttribute("user");

        if (admin == null || admin.getRole()!=Role.ADMIN){
            return "redirect:/login";
        }

        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.getTasksForUser(user));

        return "admin-user-profile";
    }
}

