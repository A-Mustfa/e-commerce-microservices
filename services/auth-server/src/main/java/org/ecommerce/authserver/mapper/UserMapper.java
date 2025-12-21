package org.ecommerce.authserver.mapper;

import org.ecommerce.authserver.dto.UserRegisterRequest;
import org.ecommerce.authserver.dto.UserRegisterResponse;
import org.ecommerce.authserver.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRegisterRequest userRegisterRequest);
    UserRegisterResponse toRegisterResponse(User user);

}
