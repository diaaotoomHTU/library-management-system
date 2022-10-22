package com.example.librarymanagementsystem.repo;

import com.example.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.librarymanagementsystem.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepo extends JpaRepository<Author, Integer>{
    Optional<Author> findAuthorById(Integer id);

    void deleteAuthorById(Integer id);

    @Query(value="select * from Author e where e.name like %:authorname%", nativeQuery = true)
    List<Author> findByAuthorName(@Param("authorname") String authorname);

    @Query(value="select * from Author e where e.id like %:authorid%", nativeQuery = true)
    List<Author> findByAuthorId(@Param("authorid") Integer authorid);
}
