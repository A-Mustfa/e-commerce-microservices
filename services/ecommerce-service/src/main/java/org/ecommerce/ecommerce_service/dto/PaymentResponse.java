package org.ecommerce.ecommerce_service.dto;

import lombok.Builder;

@Builder
public record PaymentResponse(
        String paymentId,
        Long userId,
        String status
) {}
