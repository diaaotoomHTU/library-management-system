package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.exceptions.NotFoundException;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book addBook(Book book) {
        return bookRepo.save(book);
    }
    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    public Book updateBook(Book book) {
        return bookRepo.save(book);
    }

    public Book findBookById(Integer id) {
        return bookRepo.findBookById(id).orElseThrow(() -> new NotFoundException("Not Found"));
    }

    public void deleteBook(Integer id) {
        bookRepo.deleteBookById(id);
    }

    public List<Book> findByBookId(Integer id) {
        return bookRepo.findByBookId(id);
    }

    public List<Book> findByBookName(String name) {
        return bookRepo.findByBookName(name);
    }

}
