package com.worknest.mapper;

import com.worknest.dto.UserBasicDto;
import com.worknest.dto.UserViewDto;
import com.worknest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // HR / MANAGER LIST
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    UserBasicDto toBasicDto(User user);

    // ADMIN / PROFILE VIEW
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "manager.fullName", target = "managerName")
    UserViewDto toViewDto(User user);
}
