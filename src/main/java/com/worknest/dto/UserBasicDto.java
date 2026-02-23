package com.worknest.dto;

import lombok.Data;

@Data
public class UserBasicDto {
    private Long id;
    private String fullName;
    private String email;
    private String role;

}

