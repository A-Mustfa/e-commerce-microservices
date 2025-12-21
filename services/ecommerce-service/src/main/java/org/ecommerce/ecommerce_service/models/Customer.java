package org.ecommerce.ecommerce_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Customer {

    @Id
    @Column(name = "user_id")
    private Long userId;
    private String name;
    private String address;
    private String phone;
    @CreationTimestamp
    private OffsetDateTime createdAt;
}
