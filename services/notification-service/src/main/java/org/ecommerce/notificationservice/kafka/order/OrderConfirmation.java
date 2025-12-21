package org.ecommerce.notificationservice.kafka.order;

import java.math.BigDecimal;

public record OrderConfirmation(
        Long orderId,
        String customerEmail,
        BigDecimal totalAmount,
        Customer customer
) {
}
