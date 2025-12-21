package org.ecommerce.ecommerce_service.proxies;

import org.ecommerce.ecommerce_service.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.ecommerce.ecommerce_service.dto.PaymentRequest;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentProxy {

    @PostMapping(value = "/api/v1/payments/purchase")
    PaymentResponse purchase(PaymentRequest purchaseRequest);
}
