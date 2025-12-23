package org.ecommerce.ecommerce_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.ecommerce.ecommerce_service.dto.order.OrderResponse;
import org.ecommerce.ecommerce_service.mappers.OrderMapper;
import org.ecommerce.ecommerce_service.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@AuthenticationPrincipal Jwt jwt) {
        OrderResponse responseOrder = orderMapper.toOrderResponse(orderService.placeOrder(jwt.getClaim("userId"),jwt.getClaim("email")));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        OrderResponse response =  orderMapper.toOrderResponse(orderService.cancelOrder(orderId));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        OrderResponse response = orderMapper.toOrderResponse(
                orderService.getOrderById(orderId)
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal Jwt jwt) {
        List<OrderResponse> orders = orderService.getUserOrders(jwt.getClaim("userId")).stream()
                .map(orderMapper::toOrderResponse)
                .toList();
        return ResponseEntity.ok().body(orders);
    }
}
