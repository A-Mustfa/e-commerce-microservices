package org.ecommerce.notificationservice.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.ecommerce.notificationservice.kafka.order.OrderConfirmation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private NotificationType type;
    private LocalDateTime date;
    private Long orderId;
    private String customerEmail;
    private BigDecimal totalAmount;
//    private OrderConfirmation orderConfirmation;
    // private PaymentConfirmation paymentConfirmation;

}
