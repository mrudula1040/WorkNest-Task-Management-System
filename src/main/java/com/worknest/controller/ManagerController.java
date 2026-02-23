package com.worknest.controller;

import com.worknest.entity.Role;
import com.worknest.entity.User;
import com.worknest.service.TaskService;
import com.worknest.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final UserService userService;
    private final TaskService taskService;

    // ================= MANAGER DASHBOARD =================
    @GetMapping("/dashboard")
    public String managerDashboard(HttpSession session, Model model) {

        User manager = (User) session.getAttribute("user");
        if (manager == null ||manager.getRole()!= Role.MANAGER) {
            return "redirect:/login";
        }

        model.addAttribute("team", userService.getTeamMembers(manager));
        return "manager-dashboard"; // alag HTML file
    }

    // ================= TEAM MEMBER TASKS =================
    @GetMapping("/team/{id}/tasks")
    public String viewTeamTasks(@PathVariable Long id,
                                HttpSession session,
                                Model model) {

        User manager = (User) session.getAttribute("user");
        if (manager == null || manager.getRole()!= Role.MANAGER) {
            return "redirect:/login";
        }

        User employee = userService.getUserById(id);

        // 🔐 security: sirf apni team ka data
        if (employee == null ||
                employee.getManager() == null ||
                !employee.getManager().getId().equals(manager.getId())) {

            return "redirect:/manager/dashboard";
        }

        model.addAttribute("user", employee);
        model.addAttribute("tasks", taskService.getTasksForUser(employee));

        return "manager-team-tasks";
    }
}
