package com.bookstore.onlinebookstore.respository;

import com.bookstore.onlinebookstore.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {

}
