package org.taskmanagement.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterationResponse {
    private String email;
    private String role;
}

