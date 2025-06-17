package com.bookstore.onlinebookstore.respository;

import com.bookstore.onlinebookstore.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByUserId(Long customerId, Pageable pageable);
    Page<Review> findByBook_id(Long bookId, Pageable pageable);
    boolean existsByUserIdAndBookId(Long customerId, Long bookId);
}
