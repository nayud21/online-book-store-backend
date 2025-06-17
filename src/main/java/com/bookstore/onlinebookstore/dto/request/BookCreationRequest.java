package com.bookstore.onlinebookstore.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreationRequest {
    String title;
    String author;
    String isbn;
    String description;
    Double price;
    int stock_quantity;
    String cover_image_url;
    int publication_year;
    String publisher;
    int category_id;
}
