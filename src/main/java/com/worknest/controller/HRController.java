package com.worknest.controller;

import com.worknest.entity.Role;
import com.worknest.entity.User;
import com.worknest.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hr")
public class HRController {

    private final UserService userService;

    @GetMapping("/workspace")
    public String hrWorkspace(HttpSession session, Model model) {

        User hr = (User) session.getAttribute("user");
        if (hr == null || hr.getRole()!= Role.HR) {
            return "redirect:/login";   }

        model.addAttribute("employees", userService.getAllEmployeesEntity());
        model.addAttribute("managers", userService.getAllManagers());

        return "hr-dashboard";
    }

    /*@PostMapping("/assign-manager")
    public String assignManager(@RequestParam Long userId,
                                @RequestParam Long managerId,
                                HttpSession session) {

        User hr = (User) session.getAttribute("user");
        if (hr == null || !"HR".equalsIgnoreCase(hr.getRole())) {
            return "redirect:/login";
        }

        userService.assignManager(userId, managerId);
        return "redirect:/hr/dashboard";
    }*/

    @PostMapping("/assign-manager")
    public String assignManager(@RequestParam("employeeId") Long userId,
                                @RequestParam Long managerId,
                                HttpSession session) {

        User hr = (User) session.getAttribute("user");
        if (hr == null ||hr.getRole()!= Role.HR ) {
            return "redirect:/login";
        }

        userService.assignManager(userId, managerId);
        return "redirect:/hr/workspace";
    }
    @GetMapping("/employees")
    public String employees(HttpSession session, Model model) {

        User hr = (User) session.getAttribute("user");
        if (hr == null || hr.getRole()!= Role.HR) {
            return "redirect:/login";
        }

        model.addAttribute("users", userService.getAllEmployees());
        return "hremployees";
    }

}
