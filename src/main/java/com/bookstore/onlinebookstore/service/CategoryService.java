package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.dto.request.ApiResponse;
import com.bookstore.onlinebookstore.dto.request.CategoryCreationRequest;
import com.bookstore.onlinebookstore.dto.request.CategoryUpdateRequest;
import com.bookstore.onlinebookstore.dto.response.CategoryResponse;
import com.bookstore.onlinebookstore.entity.Category;
import com.bookstore.onlinebookstore.mapper.BookMapper;
import com.bookstore.onlinebookstore.mapper.CategoryMapper;
import com.bookstore.onlinebookstore.respository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    BookMapper bookMapper;
    CategoryMapper categoryMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse addCategory(CategoryCreationRequest request){
        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException(("Category not found"))
        );
        categoryMapper.updateCategory(request, category);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public Page<CategoryResponse> getAllCategories(Pageable pageable){
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(categoryMapper::toCategoryResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategoryById(Long id){
        categoryRepository.deleteById(id);
    }
    public CategoryResponse getCategoryById(Long id){
        return categoryRepository.findById(id).map(categoryMapper::toCategoryResponse).orElseThrow(
                () -> new RuntimeException("Category not found")
        );
    }
}
