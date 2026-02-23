package com.worknest.mapper;

import com.worknest.dto.TaskCreateDto;
import com.worknest.dto.TaskResponseDto;
import com.worknest.dto.TaskViewDto;
import com.worknest.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    // ================= CREATE =================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Task toEntity(TaskCreateDto dto);

    // ================= UPDATE =================
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(TaskCreateDto dto,
                             @MappingTarget Task task);

    // ================= VIEW =================
    @Mapping(source = "id", target = "taskId")
    @Mapping(source = "user.id", target = "assignedUserId")
    @Mapping(source = "user.fullName", target = "assignedUserName")
    TaskViewDto toViewDto(Task task);

    // ================= RESPONSE =================
    @Mapping(source = "id", target = "taskId")
    TaskResponseDto toResponseDto(Task task);

    // ================= EDIT =================
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "assignedUserId")
    TaskCreateDto toCreateDto(Task task);
}
