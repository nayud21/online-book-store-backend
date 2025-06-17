package com.bookstore.onlinebookstore.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {
    Long orderId;
    @NotNull(message = "Book ID cannot be null")
    Long bookId;
    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity;
}
