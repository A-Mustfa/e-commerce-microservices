package org.ecommerce.ecommerce_service.dto.order;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.ecommerce_service.models.Order;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private String orderDeliveryAddress;
    private String customerPhone;
    private BigDecimal orderTotalAmount;
    private List<OrderItemResponse> orderItems;
    private Order.OrderStatus orderStatus;
}