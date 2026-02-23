package com.worknest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @Column(length = 100)
    private String description;


    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String category; //work,personal,urgent

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDate dueDate;
    private LocalDate createdAt;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TaskComment> comments;


}
