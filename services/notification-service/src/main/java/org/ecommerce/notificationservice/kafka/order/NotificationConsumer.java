package org.ecommerce.notificationservice.kafka.order;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ecommerce.notificationservice.email.EmailService;
import org.ecommerce.notificationservice.notification.Notification;
import org.ecommerce.notificationservice.notification.NotificationRepository;
import org.ecommerce.notificationservice.notification.NotificationType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmation(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Received Order Confirmation: {}", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .date(LocalDateTime.now())
                        .orderId(orderConfirmation.orderId())
                        .customerEmail(orderConfirmation.customerEmail())
                        .totalAmount(orderConfirmation.totalAmount())
                        .build()
        );
        var customerName = orderConfirmation.customer().getName();
        emailService.sendOrderEmail(orderConfirmation.customerEmail(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderId()
        );
    }

}
