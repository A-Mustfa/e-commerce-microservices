package org.ecommerce.payment.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.utils.Exceptions.InsufficientFundsException;
import commons.utils.Exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ecommerce.payment.dto.PaymentRequest;
import org.ecommerce.payment.dto.CustomerData;
import org.ecommerce.payment.dto.PaymentResponse;
import org.ecommerce.payment.entities.Payment;
import org.ecommerce.payment.mapper.PaymentMapper;
import org.ecommerce.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final ObjectMapper objectMapper;
    private final PaymentMapper paymentMapper;

    @Transactional(noRollbackFor = InsufficientFundsException.class)
    public PaymentResponse purchaseOrder(PaymentRequest paymentRequest) throws IOException {
        double customerBalance = getCustomerBalance(paymentRequest.getUserId());
        Payment payment = Payment.builder()
                .customerId(paymentRequest.getUserId())
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .build();

        if(!validateBalance(customerBalance, paymentRequest)){
            payment.setStatus(Payment.PaymentStatus.DENIED);
            paymentRepo.save(payment);
            throw new InsufficientFundsException("Sorry! you don't have enough money");
        }
        payment.setStatus(Payment.PaymentStatus.PAYED);
        paymentRepo.save(payment);
        return paymentMapper.toPaymentResponse(payment);
    }

    private boolean validateBalance(double customerBalance, PaymentRequest paymentRequest) {
        if (Double.compare(customerBalance, paymentRequest.getAmount()) < 0) {
            return false;
        }
        return true;
    }

    public List<PaymentResponse> getAllPayments() {
        List<Payment> existingPayments = paymentRepo.findAllPayments()
                .orElseThrow(
                       () -> new ResourceNotFoundException("No Payments found")
        );
        return existingPayments.stream()
                .map(paymentMapper::toPaymentResponse)
                .collect(Collectors.toList());
    }

    private double getCustomerBalance(Long userId) throws IOException {
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream("customers/customer.json");

        List<CustomerData> customers = objectMapper.readValue(stream, new TypeReference<List<CustomerData>>() {
        });
        double customerBalance = customers.stream()
                .filter(customer -> customer.getId() == userId)
                .map(customer -> customer.getBalance())
                .findAny().orElseThrow(() -> new EntityNotFoundException());
        return customerBalance;
    }

}
