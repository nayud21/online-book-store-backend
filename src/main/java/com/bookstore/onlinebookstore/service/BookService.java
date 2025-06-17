package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.dto.request.BookCreationRequest;
import com.bookstore.onlinebookstore.dto.request.BookUpdateRequest;
import com.bookstore.onlinebookstore.dto.response.BookResponse;
import com.bookstore.onlinebookstore.entity.Book;
import com.bookstore.onlinebookstore.entity.Category;
import com.bookstore.onlinebookstore.exception.AppException;
import com.bookstore.onlinebookstore.exception.ErrorCode;
import com.bookstore.onlinebookstore.mapper.BookMapper;
import com.bookstore.onlinebookstore.respository.BookRepository;
import com.bookstore.onlinebookstore.respository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    CategoryRepository categoryRepository;
    BookMapper bookMapper;
    BookRepository bookRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse addBook(BookCreationRequest request) {
        if (bookRepository.existsByTitle(request.getTitle()))
            throw new AppException(ErrorCode.EXISTED);

        Book book = bookMapper.toBook(request);
        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse updateBook(Long id, BookUpdateRequest request){
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Book not found!")
        );
        bookMapper.updateBook(book,request);

        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> getBooks(Pageable pageable){
        log.info("In method get all books with pagination");
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return bookPage.map(bookMapper::toBookResponse);
    }

    /**
     * Search books with multiple criteria
     */
    @Transactional(readOnly = true)
    public Page<BookResponse> searchBooks(String title, String author, String category, Pageable pageable) {
        log.info("Searching books with title: {}, author: {}, category: {}", title, author, category);

        Specification<Book> spec = createBookSpecification(title, author, category);
        Page<Book> bookPage = bookRepository.findAll(spec, pageable);

        return bookPage.map(bookMapper::toBookResponse);
    }

    /**
     * Create dynamic specification for book search
     */
    private Specification<Book> createBookSpecification(String title, String author, String category) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // If searching same term in both title and author, use OR
            if (StringUtils.hasText(title) && StringUtils.hasText(author) && title.equals(author)) {
                Predicate titlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
                Predicate authorPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("author")),
                        "%" + author.toLowerCase() + "%"
                );

                predicates.add(criteriaBuilder.or(titlePredicate, authorPredicate));
            } else {
                // Separate searches use AND
                if (StringUtils.hasText(title)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")),
                            "%" + title.toLowerCase() + "%"
                    ));
                }
                if (StringUtils.hasText(author)) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("author")),
                            "%" + author.toLowerCase() + "%"
                    ));
                }
            }

            if (StringUtils.hasText(category)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("category").get("name")),
                        "%" + category.toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public List<BookResponse> getAllBooks(){
        return bookRepository.findAll().stream().map(bookMapper::toBookResponse).toList();
    }

    public BookResponse getBookByTitle(String title){
        return bookMapper.toBookResponse(bookRepository.findByTitle(title));
    }

    public Page<BookResponse> getBookByCategory(Long categoryId, Pageable pageable){
        if(!categoryRepository.findById(categoryId).isPresent())
            throw new AppException(ErrorCode.NOT_FOUND);
        Page<Book> bookPage = bookRepository.findByCategoryId(categoryId, pageable);

        return bookPage.map(bookMapper::toBookResponse);
    }

    public BookResponse getBookById(Long id){
        return bookMapper.toBookResponse(bookRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Book not found!")));
    }

    public BookResponse getBookByIsbn(String isbn){
        return bookMapper.toBookResponse(bookRepository.findByIsbn(isbn));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }
}