package com.bookstore.onlinebookstore.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateRequest {
    String title;
    String author;
    String isbn;
    String publisher;
    String description;
    int publication_year;
    int category_id;
    int stock_quantity;
    Double price;
}
