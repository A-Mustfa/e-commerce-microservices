package org.taskmanagement.orderservice.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {

    private Long customerId;
    @Min(value = 1, message = "please enter valid quantity")
    private Integer quantity;
    private Long itemId;
}