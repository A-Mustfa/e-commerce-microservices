package org.ecommerce.customer.dtos;

import lombok.Builder;

@Builder
public record CustomerResponse(
        Long id,
        String name,
        String email) {
}
