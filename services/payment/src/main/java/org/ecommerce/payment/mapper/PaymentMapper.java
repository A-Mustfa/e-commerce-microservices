package org.ecommerce.payment.mapper;

import org.ecommerce.payment.dto.PaymentRequest;
import org.ecommerce.payment.dto.PaymentResponse;
import org.ecommerce.payment.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentResponse toPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .customerId(payment.getCustomerId())
                .orderId(payment.getOrderId())
                .status(payment.getStatus())
                .amount(payment.getAmount())
                .creationDate(payment.getCreatedAt())
                .build();
    }

    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .customerId(paymentRequest.getCustomerId())
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .build();
    }
}
