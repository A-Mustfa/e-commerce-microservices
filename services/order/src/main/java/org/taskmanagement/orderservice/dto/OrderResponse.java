package org.taskmanagement.orderservice.dto;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.taskmanagement.orderservice.models.Order;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long customerId;
    private String orderDeliveryAddress;
    private String customerPhone;
    private BigDecimal orderTotalAmount;
    private List<OrderItemResponse> orderItems;
    private Order.OrderStatus orderStatus;
    private String orderStatusName;
}