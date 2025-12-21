package org.ecommerce.authserver.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String email,
        String role
) {}
