package org.ecommerce.ecommerce_service.dto;

import org.ecommerce.ecommerce_service.models.Customer;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        Long orderId,
        String customerEmail,
        BigDecimal totalAmount,
        Customer customer
) {
}
