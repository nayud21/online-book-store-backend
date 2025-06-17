package com.bookstore.onlinebookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    Long id;
    String title;
    String author;
    String publisher;
    String isbn;
    String categoryName;
    Double price;
    String description;
    int publication_year;
    int stockQuantity;
    String cover_image_url;
}
