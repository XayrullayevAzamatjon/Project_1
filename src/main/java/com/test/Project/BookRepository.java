package com.test.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %?1%")
    List<Book> findByTitleContainingIgnoreCase(String character);

    List<Book> findAllByOrderByTitleDesc();
}
