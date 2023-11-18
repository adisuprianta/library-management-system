package com.enigma.library_management_system.repository;

import com.enigma.library_management_system.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,String> {
    Optional<Book> findBookByTitle(String title);
}
