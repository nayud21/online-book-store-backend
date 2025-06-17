package com.bookstore.onlinebookstore.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminOrderResponse {
    long id;
    OffsetDateTime orderDate;
    String status;
    BigDecimal totalAmount;

    String customerName;
    String customerEmail;
    Long customerId;
    String shippingAddress;

    List<AdminOrderItemResponse> orderItemResponses;
}
