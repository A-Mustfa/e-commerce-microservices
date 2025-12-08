package org.ecommerce.payment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ecommerce.payment.dto.PaymentRequest;
import org.ecommerce.payment.entities.Payment;
import org.ecommerce.payment.exceptions.InsufficientFundsException;
import org.ecommerce.payment.repository.PaymentRepository;
import org.ecommerce.payment.utils.Consts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;

    @Transactional(noRollbackFor = InsufficientFundsException.class)
    public Payment purchaseOrder(PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                .customerId(paymentRequest.getCustomerId())
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .build();

        if (Consts.STATIC_AMOUNT.compareTo(paymentRequest.getAmount()) < 0) {
            payment.setStatus(Payment.PaymentStatus.DENIED);
            paymentRepo.save(payment);
            throw new InsufficientFundsException("you do not have enough money!!");
        };
        payment.setStatus(Payment.PaymentStatus.PAYED);
        return paymentRepo.save(payment);
    }


    public List<Payment> getAllPayments() {
        List<Payment> existingPayments = paymentRepo.findAllPayments()
                .orElseThrow(
                       () -> new EntityNotFoundException("No Payments found")
        );
        return existingPayments;
    }

}
