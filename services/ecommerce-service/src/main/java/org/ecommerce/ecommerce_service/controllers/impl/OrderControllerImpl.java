package org.ecommerce.ecommerce_service.controllers.impl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.ecommerce.ecommerce_service.dto.order.OrderResponse;
import org.ecommerce.ecommerce_service.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerJWT")
@Tag(name = "Order", description = "Operations related to placing and managing orders")
public class OrderControllerImpl implements org.ecommerce.ecommerce_service.controllers.OrderController {

    private final OrderService orderService;

    @PostMapping
    @Override
    public ResponseEntity<OrderResponse> placeOrder(@AuthenticationPrincipal Jwt jwt) {
        OrderResponse responseOrder = orderService.placeOrder(jwt.getClaim("userId"),jwt.getClaim("email"));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @DeleteMapping("/{orderId}")
    @Override
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        OrderResponse response =  orderService.cancelOrder(orderId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{orderId}")
    @Override
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        OrderResponse response = orderService.getOrderById(orderId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal Jwt jwt) {
        List<OrderResponse> orders = orderService.getUserOrders(jwt.getClaim("userId"));
        return ResponseEntity.ok().body(orders);
    }
}
