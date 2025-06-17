package com.bookstore.onlinebookstore.mapper;

import com.bookstore.onlinebookstore.dto.request.BookCreationRequest;
import com.bookstore.onlinebookstore.dto.request.BookUpdateRequest;
import com.bookstore.onlinebookstore.dto.response.BookResponse;
import com.bookstore.onlinebookstore.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target ="category", ignore = true)
    Book toBook(BookCreationRequest request);

    @Mapping(source = "stockQuantity", target = "stockQuantity")
    @Mapping(source = "cover_image_url", target = "cover_image_url")
    @Mapping(source = "category.name", target ="categoryName")
    BookResponse toBookResponse(Book book);

    @Mapping(target ="category", ignore = true)
    void updateBook(@MappingTarget  Book book, BookUpdateRequest request);
}
