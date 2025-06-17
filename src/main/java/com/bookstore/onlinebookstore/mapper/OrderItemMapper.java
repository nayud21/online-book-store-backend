package com.bookstore.onlinebookstore.mapper;

import com.bookstore.onlinebookstore.dto.response.AdminOrderItemResponse;
import com.bookstore.onlinebookstore.dto.response.OrderItemResponse;
import com.bookstore.onlinebookstore.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "book.id", target = "bookId")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    AdminOrderItemResponse toAdminOrderItemResponse(OrderItem orderItem);
}
