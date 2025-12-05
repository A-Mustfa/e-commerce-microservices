package org.taskmanagement.userservice.mappers;

import org.mapstruct.Mapping;
import org.taskmanagement.userservice.dto.RegisterationResponse;
import org.taskmanagement.userservice.entities.Role;
import org.taskmanagement.userservice.entities.User;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    @Mapping(source = "role",target = "role")
    RegisterationResponse toResponse(User user);

    default String map(Role role) {
        return role != null ? role.getName() : null;
    }
}
