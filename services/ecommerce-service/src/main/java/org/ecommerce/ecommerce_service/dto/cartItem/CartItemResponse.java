package org.ecommerce.ecommerce_service.dto.cartItem;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItemResponse(Long id,
                               Long itemId,
                               String itemName,
                               Integer quantity,
                               BigDecimal unitPrice,
                               BigDecimal totalPrice) {
}
