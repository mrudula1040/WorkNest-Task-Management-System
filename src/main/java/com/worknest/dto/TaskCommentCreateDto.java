package com.worknest.dto;

import lombok.Data;

@Data
public class TaskCommentCreateDto {
    private Long taskId;
    private String comment;
}
