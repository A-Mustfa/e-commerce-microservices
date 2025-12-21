package org.ecommerce.ecommerce_service.dto.cartItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {
    @Min(value = 1, message = "please enter valid quantity")
    private Integer quantity;
    @NotNull
    private Long itemId;
}