package com.example.librarymanagementsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.librarymanagementsystem.model.Librarian;

import java.util.Optional;

public interface LibrarianRepo extends JpaRepository<Librarian, Integer> {
    Optional<Librarian> findLibrarianById(Integer id);
    void deleteLibrarianById(Integer id);
}
