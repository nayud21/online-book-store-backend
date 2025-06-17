package com.bookstore.onlinebookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    User user;

    OffsetDateTime orderDate;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    String status;

    @Column(name = "shipping_address")
    String shippingAddress;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "payment_status")
    String paymentStatus;

    @Column(updatable = false)
    OffsetDateTime created_at;
    OffsetDateTime updated_at;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems;

    @PrePersist
    protected void onCreate(){
        OffsetDateTime now = OffsetDateTime.now();
        this.created_at = now;
        this.updated_at = now;
        this.orderDate = now;
    }
    @PreUpdate void onUpdate(){
        this.updated_at = OffsetDateTime.now();
    }

}
