package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.dto.request.ApiResponse;
import com.bookstore.onlinebookstore.dto.request.BookCreationRequest;
import com.bookstore.onlinebookstore.dto.request.BookUpdateRequest;
import com.bookstore.onlinebookstore.dto.response.BookResponse;
import com.bookstore.onlinebookstore.entity.Book;
import com.bookstore.onlinebookstore.respository.BookRepository;
import com.bookstore.onlinebookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    BookService bookService;

    @GetMapping
    ApiResponse<Page<BookResponse>> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            Pageable pageable){

        // If no search parameters, use original getBooks method
        if (!StringUtils.hasText(title) && !StringUtils.hasText(author) && !StringUtils.hasText(category)) {
            return ApiResponse.<Page<BookResponse>>builder()
                    .result(bookService.getBooks(pageable))
                    .build();
        }

        // Use search method if any parameter is provided
        return ApiResponse.<Page<BookResponse>>builder()
                .result(bookService.searchBooks(title, author, category, pageable))
                .build();
    }
    @GetMapping("/title/{title}")
    ApiResponse<BookResponse> searchBooks(@PathVariable("title") String title){
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBookByTitle(title))
                .build();
    }

    @GetExchange("/category/{category}")
    ApiResponse<Page<BookResponse>> searchBooksByCategory(@PathVariable("category") Long category, Pageable pageable){
        return ApiResponse.<Page<BookResponse>>builder()
                .result(bookService.getBookByCategory(category,pageable))
                .build();
    }
    @GetMapping("/{id}")
    ApiResponse<BookResponse> getBookById(@PathVariable("id") Long id){
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBookById(id))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ApiResponse<BookResponse> createBook(@RequestBody @Valid BookCreationRequest request){
        ApiResponse<BookResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(bookService.addBook(request));

        return apiResponse;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{bookId}")
    ApiResponse<BookResponse> updateBook(@PathVariable Long bookId, @RequestBody @Valid BookUpdateRequest request){
        ApiResponse<BookResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(bookService.updateBook(bookId,request));
        return apiResponse;
    }




}
