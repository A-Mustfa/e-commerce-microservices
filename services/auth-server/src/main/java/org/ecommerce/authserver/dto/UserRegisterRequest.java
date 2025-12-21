package org.ecommerce.authserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.ecommerce.authserver.entities.User;

@Builder
public record UserRegisterRequest(
        @Email
        String email,
        @Size(min = 8, message = "please enter 8 digits")
        String password,
        @NotNull(message = "you had to specify: (CUSTOMER - ADMIN) ")
        User.Role role) {
}
