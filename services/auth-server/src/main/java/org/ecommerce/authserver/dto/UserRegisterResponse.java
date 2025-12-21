package org.ecommerce.authserver.dto;

import lombok.Builder;

@Builder
public record UserRegisterResponse(
        Long id,
        String email,
        String role
) {}
