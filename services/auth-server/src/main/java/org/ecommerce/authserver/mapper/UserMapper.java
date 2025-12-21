package org.ecommerce.authserver.mapper;

import org.ecommerce.authserver.dto.RegisterRequest;
import org.ecommerce.authserver.dto.UserResponse;
import org.ecommerce.authserver.entities.User;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequest registerRequest);
    UserResponse toRegisterResponse(User user);

}
