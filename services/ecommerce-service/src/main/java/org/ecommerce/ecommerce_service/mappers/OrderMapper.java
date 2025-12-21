package org.ecommerce.ecommerce_service.mappers;

import org.springframework.stereotype.Component;
import org.ecommerce.ecommerce_service.dto.order.OrderItemResponse;
import org.ecommerce.ecommerce_service.dto.order.OrderResponse;
import org.ecommerce.ecommerce_service.models.Order;
import org.ecommerce.ecommerce_service.models.OrderItem;

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
                .userId(order.getUserId())
                .orderStatus(order.getOrderStatus())
                .orderDeliveryAddress(order.getOrderDeliveryAddress())
                .orderTotalAmount(order.getAmount())
                .customerPhone(order.getCustomerPhone())
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