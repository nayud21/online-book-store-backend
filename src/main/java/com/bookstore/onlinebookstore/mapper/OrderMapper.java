package com.bookstore.onlinebookstore.mapper;

import com.bookstore.onlinebookstore.dto.response.AdminOrderResponse;
import com.bookstore.onlinebookstore.dto.response.OrderResponse;
import com.bookstore.onlinebookstore.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItemResponses")
    OrderResponse toOrderResponse(Order order);

    @Mapping(source = "user.email", target = "customerEmail")
    @Mapping(source = "user.full_name", target = "customerName")
    @Mapping(source = "orderItems", target = "orderItemResponses")
    @Mapping(source = "user.id", target = "customerId")
    @Mapping(source = "user.address", target = "shippingAddress")
    AdminOrderResponse toAdminOrderResponse(Order order);
}
