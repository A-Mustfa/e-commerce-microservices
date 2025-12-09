package org.ecommerce.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.payment.dto.PaymentRequest;
import org.ecommerce.payment.dto.PaymentResponse;
import org.ecommerce.payment.entities.Payment;
import org.ecommerce.payment.mapper.PaymentMapStruct;
import org.ecommerce.payment.mapper.PaymentMapper;
import org.ecommerce.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PaymentResponse> purchase(@RequestBody @Valid PaymentRequest paymentRequest) throws IOException {
        PaymentResponse paymentResponse = paymentMapper.toPaymentResponse(paymentService.purchaseOrder(paymentRequest));
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> listPayments() {
        List<PaymentResponse> response = paymentService.getAllPayments().stream()
                .map(paymentMapStruct::toDtoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

}
