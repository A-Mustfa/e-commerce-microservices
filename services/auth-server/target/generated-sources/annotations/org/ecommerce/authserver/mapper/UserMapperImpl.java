package org.ecommerce.authserver.mapper;

import javax.annotation.processing.Generated;
import org.ecommerce.authserver.dto.RegisterRequest;
import org.ecommerce.authserver.dto.UserResponse;
import org.ecommerce.authserver.entities.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-21T14:33:16+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(RegisterRequest registerRequest) {
        if ( registerRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( registerRequest.email() );
        user.password( registerRequest.password() );
        user.role( registerRequest.role() );

        return user.build();
    }

    @Override
    public UserResponse toRegisterResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.email( user.getEmail() );
        if ( user.getRole() != null ) {
            userResponse.role( user.getRole().name() );
        }

        return userResponse.build();
    }
}
