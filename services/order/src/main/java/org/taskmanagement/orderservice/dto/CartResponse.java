package org.taskmanagement.orderservice.dto;

import lombok.Builder;
import org.taskmanagement.orderservice.models.CartItem;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
public record CartResponse(
        Long cartId,
        Long customerId,
        OffsetDateTime createdAt,
        List<CartItemResponse> items,
        BigDecimal totalPrice,
        Integer totalQuantity
) {
}
