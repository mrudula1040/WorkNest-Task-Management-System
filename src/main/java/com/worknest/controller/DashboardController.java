package com.worknest.controller;

import com.worknest.entity.Role;
import com.worknest.entity.TaskStatus;
import com.worknest.entity.User;
import com.worknest.service.TaskService;
import com.worknest.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null ) {
            return "redirect:/login";
        }
Role role = user.getRole();

        // ================= ADMIN =================
        if (user.getRole()==Role.ADMIN) {
            model.addAttribute("totalUsers", userService.getTotalUsersCount());
            model.addAttribute("totalTasks", taskService.getTotalTasksCount());

            long completed = taskService.getCountByStatusForUsers(TaskStatus.DONE);
            long pending = taskService.getCountByStatusForUsers(TaskStatus.TODO)
                    + taskService.getCountByStatusForUsers(TaskStatus.IN_PROGRESS);

            model.addAttribute("completedTasks", completed);
            model.addAttribute("pendingTasks", pending);
            model.addAttribute("overdueCount", taskService.getOverdueCount());
            model.addAttribute("recentTasks", taskService.getAllTasks().stream().limit(5).toList());

        }

        // ================= MANAGER =================
        if (user.getRole()==Role.MANAGER) {
            model.addAttribute("team", userService.getTeamMembers(user));
        }

        // ================= HR =================
        if (user.getRole()==Role.HR) {
            model.addAttribute("totalEmployees", userService.getTotalUsersCount());
        }

        return "dashboard";
    }
}
