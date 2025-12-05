package org.taskmanagement.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.taskmanagement.userservice.entities.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotEmpty
    @Email
    private String email;
    @NotNull
    @Size(min = 8)
    private String password;
    @NotNull
    private String role;
}
