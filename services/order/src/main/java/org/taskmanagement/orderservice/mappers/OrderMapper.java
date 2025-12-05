package org.taskmanagement.orderservice.mappers;

import org.springframework.stereotype.Component;
import org.taskmanagement.orderservice.dto.OrderItemResponse;
import org.taskmanagement.orderservice.dto.OrderResponse;
import org.taskmanagement.orderservice.models.Order;
import org.taskmanagement.orderservice.models.OrderItem;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderMapper {

    public OrderResponse toOrderResponse(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .orderStatus(order.getOrderStatus())
                .orderStatusName(order.getOrderStatus().getStatusName())
                .orderDeliveryAddress(order.getOrderDeliveryAddress())
                .orderTotalAmount(order.getOrderTotalAmount())
                .customerPhone("order.getCustomerPhone()")
                .orderItems(toOrderItemResponses(order.getOrderItems()))
                .build();
    }

    private List<OrderItemResponse> toOrderItemResponses(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return List.of();
        }

        return orderItems.stream()
                .map(this::toOrderItemResponse)
                .toList();
    }

    private OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        BigDecimal unitPrice = orderItem.getTotalPrice()
                .divide(BigDecimal.valueOf(orderItem.getQuantity()), 2, java.math.RoundingMode.HALF_UP);

        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .itemId(orderItem.getItemId())
                .name(orderItem.getName())
                .quantity((int) orderItem.getQuantity())
                .unitPrice(unitPrice)
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}