package org.ecommerce.customer.models;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role=Role.CUSTOMER;

    private String phone;

    private String address;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "cart_id")
    private Long cartId;

    public enum Role {
        CUSTOMER,
        ADMIN
    }
}
