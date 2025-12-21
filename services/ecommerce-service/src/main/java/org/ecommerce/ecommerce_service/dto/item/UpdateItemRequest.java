package org.ecommerce.ecommerce_service.dto.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
@Builder
public record UpdateItemRequest(
        @NotNull(message = "enter itemId")
        Long itemId,
        @Min(value = 0,message = "enter valid quantity")
        double quantity)  {
}
