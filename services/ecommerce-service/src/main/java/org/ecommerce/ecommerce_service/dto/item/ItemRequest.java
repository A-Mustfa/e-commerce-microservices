package org.ecommerce.ecommerce_service.dto.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ItemRequest {
    @NotNull(message = "please enter item name")
    @NotBlank
    private String name;
    @Min(value = 1, message = "enter a positive price")
    private BigDecimal price;
    @Min(value = 0,message = "please enter valid stock")
    private double stock=0;

    private String description;
}
