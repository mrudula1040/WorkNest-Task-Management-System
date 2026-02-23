package com.worknest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCommentViewDto {
    private Long id;
    private String comment;
    private String commentedBy;
    private LocalDate commentedAt;
}
