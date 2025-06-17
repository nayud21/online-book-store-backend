package com.bookstore.onlinebookstore.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminOrderItemResponse {
    Long bookId;
    String bookTitle;
    int quantity;
    BigDecimal priceAtPurchase;
}
