package com.bookstore.onlinebookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String slug;

    private String description;
    @Column(updatable = false)
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Book> books;


    @PrePersist
    protected void onCreate(){
        OffsetDateTime now = OffsetDateTime.now();
        this.created_at = now;
        this.updated_at = now;
    }
    @PreUpdate
    protected void onUpdate(){
        this.updated_at = OffsetDateTime.now();
    }
}
