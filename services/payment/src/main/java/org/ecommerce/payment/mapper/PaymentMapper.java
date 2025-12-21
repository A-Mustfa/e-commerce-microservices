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
                .status(payment.getStatus().toString())
                .build();
    }

    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .customerId(paymentRequest.getUserId())
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .build();
    }
}
