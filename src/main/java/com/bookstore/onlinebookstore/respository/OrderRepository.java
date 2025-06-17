package com.bookstore.onlinebookstore.respository;

import com.bookstore.onlinebookstore.entity.Order;
import com.bookstore.onlinebookstore.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_Username(String username, Pageable pageable);
    boolean existsByUserIdAndOrderItems_Book_Id(Long userId, Long bookId);
}
