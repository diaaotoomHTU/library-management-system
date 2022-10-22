package com.example.librarymanagementsystem.repo;

import com.example.librarymanagementsystem.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Integer>{
    Optional<Book> findBookById(Integer id);

    void deleteBookById(Integer id);

    @Query(value="select * from Book e where e.name like %:bookname%", nativeQuery = true)
    List<Book> findByBookName(@Param("bookname") String bookname);

    @Query(value="select * from Book e where e.id like %:bookid%", nativeQuery = true)
    List<Book> findByBookId(@Param("bookid") Integer bookid);

}
