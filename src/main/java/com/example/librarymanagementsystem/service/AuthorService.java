package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.exceptions.NotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepo authorRepo;

    @Autowired
    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public Author addAuthor(Author author) {
        return authorRepo.save(author);
    }

    public List<Author> findAllAuthors() {
        return authorRepo.findAll();
    }

    public Author updateAuthor(Author author) {
        return authorRepo.save(author);
    }

    public Author findAuthorById(Integer id) {
        return authorRepo.findAuthorById(id).orElseThrow(() -> new NotFoundException("Not Found"));
    }

    public void deleteAuthor(Integer id) {
        authorRepo.deleteAuthorById(id);
    }

    public List<Author> findByAuthorId(Integer id) {
        return authorRepo.findByAuthorId(id);
    }

    public List<Author> findByAuthorName(String name) {
        return authorRepo.findByAuthorName(name);
    }

}
