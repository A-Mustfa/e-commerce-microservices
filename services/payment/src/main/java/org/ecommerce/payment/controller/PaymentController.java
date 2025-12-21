package org.ecommerce.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.payment.dto.PaymentRequest;
import org.ecommerce.payment.dto.PaymentResponse;
import org.ecommerce.payment.entities.Payment;
import org.ecommerce.payment.mapper.PaymentMapStruct;
import org.ecommerce.payment.mapper.PaymentMapper;
import org.ecommerce.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;
    private final PaymentMapStruct paymentMapStruct;

    @PostMapping("/purchase")
    public ResponseEntity<PaymentResponse> purchase(@Valid @RequestBody PaymentRequest paymentRequest) throws IOException {
        PaymentResponse response = paymentMapper.toPaymentResponse(paymentService.purchaseOrder(paymentRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentResponse>> listPayments() {
        List<PaymentResponse> response = paymentService.getAllPayments().stream()
                .map(paymentMapper::toPaymentResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

}
