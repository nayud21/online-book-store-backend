package com.bookstore.onlinebookstore.respository;

import com.bookstore.onlinebookstore.entity.Order;
import com.bookstore.onlinebookstore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
