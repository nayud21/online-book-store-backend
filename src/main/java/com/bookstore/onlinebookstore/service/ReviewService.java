package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.dto.request.ReviewCreationRequest;
import com.bookstore.onlinebookstore.dto.response.ReviewResponse;
import com.bookstore.onlinebookstore.entity.Book;
import com.bookstore.onlinebookstore.entity.Review;
import com.bookstore.onlinebookstore.entity.User;
import com.bookstore.onlinebookstore.exception.AppException;
import com.bookstore.onlinebookstore.exception.ErrorCode;
import com.bookstore.onlinebookstore.mapper.ReviewMapper;
import com.bookstore.onlinebookstore.respository.BookRepository;
import com.bookstore.onlinebookstore.respository.OrderRepository;
import com.bookstore.onlinebookstore.respository.ReviewRepository;
import com.bookstore.onlinebookstore.respository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ReviewService {
    UserRepository userRepository;
    BookRepository bookRepository;
    ReviewRepository reviewRepository;
    OrderRepository orderRepository;
    ReviewMapper reviewMapper;

    @Transactional
    public ReviewResponse addReview(ReviewCreationRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        );
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );

        boolean hasPurchased = orderRepository.existsByUserIdAndOrderItems_Book_Id(user.getId(), book.getId());
        if (!hasPurchased){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return reviewMapper.toReviewResponse(reviewRepository.save(review));

    }
    public Page<ReviewResponse> getReviewsForBook(Long bookId, Pageable pageable){
        Page<Review> reviewPage = reviewRepository.findByBook_id(bookId, pageable);
        return reviewPage.map(reviewMapper::toReviewResponse);
    }
    public Page<ReviewResponse> getReviewsForUser(Pageable pageable){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        );

        Page<Review> reviewPage = reviewRepository.findByUserId(user.getId(), pageable);
        return reviewPage.map(reviewMapper::toReviewResponse);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ReviewResponse> getAllReviews(Pageable pageable){
        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        return reviewPage.map((reviewMapper::toReviewResponse));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteReview(Long reviewId){
        if (!reviewRepository.existsById(reviewId)){
            throw new AppException(ErrorCode.NOT_FOUND);
        };
        reviewRepository.deleteById(reviewId);
    }
}
