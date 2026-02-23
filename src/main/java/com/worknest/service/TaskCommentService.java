package com.worknest.service;

import com.worknest.entity.Task;
import com.worknest.entity.TaskComment;
import com.worknest.entity.User;
import com.worknest.repository.TaskCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskCommentService {

    private final TaskCommentRepository commentRepository;
    public void addComment(Task task, User user, String text) {

        TaskComment comment = new TaskComment();
        comment.setTask(task);
        comment.setUser(user);
        comment.setComment(text);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public List<TaskComment> getComments(Task task) {
        return commentRepository.findByTaskOrderByCreatedAtDesc(task);
    }


}
