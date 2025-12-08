package org.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.payment.entities.Payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long customerId;
    private Long orderId;
    private Payment.PaymentStatus status;
    private BigDecimal amount;
    private OffsetDateTime creationDate;
}
