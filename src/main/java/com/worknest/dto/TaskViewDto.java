package com.worknest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskViewDto {
    private Long taskId;
    private String title;
    private String status;
    private String priority;
    private String category;
    private Long userId;
    private String userName;
    private String assignedUserName;
    private Long assignedUserId;
    private LocalDate dueDate;
}