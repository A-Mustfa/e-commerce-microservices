package org.taskmanagement.orderservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskmanagement.orderservice.dto.PlaceOrderRequest;
import org.taskmanagement.orderservice.dto.OrderResponse;
import org.taskmanagement.orderservice.mappers.OrderMapper;
import org.taskmanagement.orderservice.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody @Valid PlaceOrderRequest orderRequest)
    {
        OrderResponse responseOrder = orderMapper.toOrderResponse(orderService.placeOrder(orderRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId)
    {
        OrderResponse response =  orderMapper.toOrderResponse(orderService.cancelOrder(orderId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        // Add this endpoint
        OrderResponse response = orderMapper.toOrderResponse(
                orderService.getOrderById(orderId)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@PathVariable Long customerId) {
        // Add this endpoint for customer order history
        List<OrderResponse> orders = orderService.getCustomerOrders(customerId).stream()
                .map(orderMapper::toOrderResponse)
                .toList();
        return ResponseEntity.ok(orders);
    }

}
