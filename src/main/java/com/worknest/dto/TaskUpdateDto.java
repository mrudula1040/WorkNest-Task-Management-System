package com.worknest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskUpdateDto {
    private Long id;

    private String title;
    private String description;
    private String category;
    private String priority;
    private LocalDate dueDate;

    private String status;
}
