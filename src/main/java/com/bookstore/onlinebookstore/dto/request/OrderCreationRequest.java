package com.bookstore.onlinebookstore.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {
    @NotEmpty(message = "Order must be contain at least one item")
    List<OrderItemRequest> orderItemRequests;

    @NotEmpty(message = "Shipping address cannot be empty")
    String shippingAddress;

    String paymentMethod;
}
