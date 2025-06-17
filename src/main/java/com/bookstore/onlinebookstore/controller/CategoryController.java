package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.dto.request.ApiResponse;
import com.bookstore.onlinebookstore.dto.request.CategoryCreationRequest;
import com.bookstore.onlinebookstore.dto.request.CategoryUpdateRequest;
import com.bookstore.onlinebookstore.dto.response.CategoryResponse;
import com.bookstore.onlinebookstore.entity.Category;
import com.bookstore.onlinebookstore.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @GetMapping
    ApiResponse<Page<CategoryResponse>> getCategories(Pageable pageable) {
        return ApiResponse.<Page<CategoryResponse>>builder()
                .result(categoryService.getAllCategories(pageable))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<CategoryResponse> getCategory(@PathVariable long id){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategoryById(id))
                .build();
    }

    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable Long categoryId,
                                                 @RequestBody @Valid CategoryUpdateRequest request){
        ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(categoryService.updateCategory(categoryId,request));
        return apiResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ApiResponse<CategoryResponse> addCategory(@RequestBody CategoryCreationRequest request){
        log.info(request.toString());
        ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(categoryService.addCategory(request));

        return apiResponse;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    ApiResponse<CategoryResponse> deleteCategory(@PathVariable long categoryId){
        categoryService.deleteCategoryById(categoryId);

        return ApiResponse.<CategoryResponse>builder()
                .message("Category deleted")
                .build();
    }

}
