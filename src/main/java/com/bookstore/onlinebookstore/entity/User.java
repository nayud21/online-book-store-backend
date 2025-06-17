package com.bookstore.onlinebookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String username;
    String password;
    String email;
    String full_name;
    String address;
    String phone_number;
    String role;
    @Column(name = "created_at", updatable = false)
    OffsetDateTime created_at;
    @Column(name = "updated_at")
    OffsetDateTime updated_at;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Order> orders;


    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now(); // Hoặc OffsetDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")) nếu muốn múi giờ cụ thể
        this.created_at = now;
        this.updated_at = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = OffsetDateTime.now(); // Hoặc OffsetDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))
    }
}
