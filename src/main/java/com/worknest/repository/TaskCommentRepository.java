package com.worknest.repository;

import com.worknest.entity.Task;
import com.worknest.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment,Long> {

    List<TaskComment> findByTaskOrderByCreatedAtDesc(Task task);

}
