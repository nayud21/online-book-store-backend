package com.bookstore.onlinebookstore.mapper;

import com.bookstore.onlinebookstore.dto.response.ReviewResponse;
import com.bookstore.onlinebookstore.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "user.full_name", target = "customerName")
    ReviewResponse toReviewResponse(Review review);
}
