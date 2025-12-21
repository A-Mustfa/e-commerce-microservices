package org.ecommerce.payment.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @NotNull(message = "user id is required")
    private Long userId;
    @NotNull(message = "order id is required")
    private Long orderId;
    @NotNull
    @Min(value = 1, message = "amount had to be more than 0")
    private Double amount;
}
