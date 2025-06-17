package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.dto.request.ApiResponse;
import com.bookstore.onlinebookstore.dto.request.ReviewCreationRequest;
import com.bookstore.onlinebookstore.dto.response.ReviewResponse;
import com.bookstore.onlinebookstore.entity.Review;
import com.bookstore.onlinebookstore.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {
    ReviewService reviewService;

    @PostMapping
    public ApiResponse<ReviewResponse> createReview(@RequestBody ReviewCreationRequest review){
        return ApiResponse.<ReviewResponse>builder()
                .result(reviewService.addReview(review))
                .build();
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<Page<ReviewResponse>> getReviewsByBook(@PathVariable Long bookId, Pageable pageable){
        return ApiResponse.<Page<ReviewResponse>>builder()
                .result(reviewService.getReviewsForBook(bookId, pageable))
                .build();
    }
    @GetMapping
    public ApiResponse<Page<ReviewResponse>> getAllReviews(Pageable pageable){
        return ApiResponse.<Page<ReviewResponse>>builder()
                .result(reviewService.getAllReviews(pageable))
                .build();
    }
    @DeleteMapping("/{reviewId}")
    public ApiResponse<String> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ApiResponse.<String>builder()
                .result("Review has been deleted")
                .build();
    }


}
