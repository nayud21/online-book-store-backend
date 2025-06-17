package com.bookstore.onlinebookstore.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    long id;
    Long userId;
    OffsetDateTime orderDate;
    String status;
    BigDecimal totalAmount;
    String shippingAddress;
    List<OrderItemResponse> orderItemResponses;
}
