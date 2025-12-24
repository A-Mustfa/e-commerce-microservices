package org.ecommerce.payment.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.payment.controller.PaymentController;
import org.ecommerce.payment.dto.PaymentRequest;
import org.ecommerce.payment.dto.PaymentResponse;
import org.ecommerce.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment", description = "Operations related to payment processing and history")
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/purchase")
    @Override
    public ResponseEntity<PaymentResponse> purchase(@Valid @RequestBody PaymentRequest paymentRequest) throws IOException {
        PaymentResponse response = paymentService.purchaseOrder(paymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<List<PaymentResponse>> listPayments() {
        List<PaymentResponse> response = paymentService.getAllPayments();
        return ResponseEntity.ok(response);
    }

}
