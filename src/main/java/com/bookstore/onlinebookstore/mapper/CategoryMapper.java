package com.bookstore.onlinebookstore.mapper;

import com.bookstore.onlinebookstore.dto.request.CategoryCreationRequest;
import com.bookstore.onlinebookstore.dto.request.CategoryUpdateRequest;
import com.bookstore.onlinebookstore.dto.response.CategoryResponse;
import com.bookstore.onlinebookstore.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(CategoryUpdateRequest request, @MappingTarget Category category);
}
