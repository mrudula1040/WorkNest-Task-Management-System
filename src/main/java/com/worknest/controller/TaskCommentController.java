package com.worknest.controller;

import com.worknest.entity.Task;
import com.worknest.entity.User;
import com.worknest.service.TaskCommentService;
import com.worknest.service.TaskService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor

public class TaskCommentController {
    private final TaskService taskService;
    private final TaskCommentService commentService;

    @PostMapping("/task/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String comment,
                             HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Task task = taskService.getTask(id);
        if (task == null) return "redirect:/tasks";

        commentService.addComment(task, user, comment);

        return "redirect:/task/edit/" + id;
    }

}
