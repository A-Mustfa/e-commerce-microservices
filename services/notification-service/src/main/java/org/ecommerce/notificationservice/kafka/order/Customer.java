package org.ecommerce.notificationservice.kafka.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Customer {

    private Long userId;
    private String name;
    private String email;
    private String address;
    private String phone;
    private OffsetDateTime createdAt;
}