package org.ecommerce.ecommerce_service.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.ecommerce.ecommerce_service.exceptions.OrderCannotBeCanceledException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String orderDeliveryAddress;

    @Column
    private String customerPhone;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus orderStatus =  OrderStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems;


    public static Order createOrderFromCart(Cart orderCart, Customer customer) {
        Order order = new Order();
        order.setUserId(customer.getUserId());
        order.setAmount(orderCart.getTotalPrice());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDeliveryAddress(customer.getAddress());
        order.setCustomerPhone(customer.getPhone());
        return order;
    }

    public void cancel() {
        if (!this.orderStatus.isCancellable()) {
            throw new OrderCannotBeCanceledException(
                    "Order in " + orderStatus.getStatusName() + " status cannot be cancelled"
            );
        }
        this.orderStatus = OrderStatus.CANCELLED;
    }

    public enum OrderStatus {
        PENDING(1L, "Pending",true),
        CONFIRMED(2L, "Confirmed", false),
        OUT_FOR_DELIVERY(5L, "Out for Delivery", false),
        DELIVERED(6L, "Delivered", false),
        CANCELLED(7L, "Cancelled", false);

        private final Long statusId;

        private final String statusName;

        private final boolean cancellable;


        OrderStatus(Long statusId, String statusName, boolean cancellable) {
            this.statusId = statusId;
            this.statusName = statusName;
            this.cancellable = cancellable;
        }

        public Long getStatusId() {
            return statusId;
        }

        public String getStatusName() {
            return statusName;
        }

        public boolean isCancellable() {
            return this.cancellable;
        }

    }

}
