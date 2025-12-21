package org.ecommerce.ecommerce_service.dto.customerdto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        Long userId,
        String name,
        String phone
) {
}
