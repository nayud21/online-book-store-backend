package com.bookstore.onlinebookstore.respository;

import com.bookstore.onlinebookstore.entity.Book;
import com.bookstore.onlinebookstore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    boolean existsByTitle(String bookName);
    Book findByTitle(String bookTitle);
    Optional<Book> findByAuthor(String author);
    Book findByIsbn(String isbn);
    Page<Book> findByCategoryId(Long categoryId, Pageable pageable);

}
