package org.ecommerce.payment.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ecommerce.payment.dto.PaymentRequest;
import org.ecommerce.payment.dto.CustomerData;
import org.ecommerce.payment.entities.Payment;
import org.ecommerce.payment.exceptions.InsufficientFundsException;
import org.ecommerce.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final ObjectMapper objectMapper;

    @Transactional(noRollbackFor = InsufficientFundsException.class)
    public Payment purchaseOrder(PaymentRequest paymentRequest) throws IOException {
        double customerBalance = getCustomerBalance(paymentRequest.getCustomerId());
        Payment payment = Payment.builder()
                .customerId(paymentRequest.getCustomerId())
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .customerBalance(customerBalance)
                .build();

        if(!validateBalance(customerBalance, paymentRequest)){
            payment.setStatus(Payment.PaymentStatus.DENIED);
            paymentRepo.save(payment);
            throw new InsufficientFundsException("Sorry boor man 🥲!! the order amount is: "
                    + paymentRequest.getAmount()
                    +", you only have: " + customerBalance + " 😂");
        }
        payment.setStatus(Payment.PaymentStatus.PAYED);
        return paymentRepo.save(payment);
    }

    private boolean validateBalance(double customerBalance, PaymentRequest paymentRequest) {
        if (Double.compare(customerBalance, paymentRequest.getAmount()) < 0) {
            return false;
        }
        return true;
    }

    public List<Payment> getAllPayments() {
        List<Payment> existingPayments = paymentRepo.findAllPayments()
                .orElseThrow(
                       () -> new EntityNotFoundException("No Payments found")
        );
        return existingPayments;
    }

    private double getCustomerBalance(Long customerId) throws IOException {
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream("customers/customer.json");

        List<CustomerData> customers = objectMapper.readValue(stream, new TypeReference<List<CustomerData>>() {
        });
        double customerBalance = customers.stream()
                .filter(customer -> customer.getId() == customerId)
                .map(customer -> customer.getBalance())
                .findAny().orElseThrow(() -> new EntityNotFoundException());
        return customerBalance;
    }

}
