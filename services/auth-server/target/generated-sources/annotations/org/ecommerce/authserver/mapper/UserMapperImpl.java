package org.ecommerce.authserver.mapper;

import javax.annotation.processing.Generated;
import org.ecommerce.authserver.dto.UserRegisterRequest;
import org.ecommerce.authserver.dto.UserRegisterResponse;
import org.ecommerce.authserver.entities.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-21T20:27:21+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRegisterRequest userRegisterRequest) {
        if ( userRegisterRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( userRegisterRequest.email() );
        user.password( userRegisterRequest.password() );
        user.role( userRegisterRequest.role() );

        return user.build();
    }

    @Override
    public UserRegisterResponse toRegisterResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserRegisterResponse.UserRegisterResponseBuilder userRegisterResponse = UserRegisterResponse.builder();

        userRegisterResponse.id( user.getId() );
        userRegisterResponse.email( user.getEmail() );
        if ( user.getRole() != null ) {
            userRegisterResponse.role( user.getRole().name() );
        }

        return userRegisterResponse.build();
    }
}
