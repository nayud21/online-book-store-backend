package com.bookstore.onlinebookstore.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    Long bookId;
    int quantity;
    BigDecimal priceAtPurchase;
}
