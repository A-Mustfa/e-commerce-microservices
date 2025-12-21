package org.ecommerce.ecommerce_service.dto.cart;

import lombok.Builder;
import org.ecommerce.ecommerce_service.dto.cartItem.CartItemResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
public record CartResponse(
        Long cartId,
        Long userId,
        OffsetDateTime createdAt,
        List<CartItemResponse> items,
        BigDecimal totalPrice,
        Integer totalQuantity
) {
}
