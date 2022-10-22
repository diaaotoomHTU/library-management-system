package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.exceptions.NotFoundException;
import com.example.librarymanagementsystem.model.Librarian;
import com.example.librarymanagementsystem.repo.LibrarianRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianService {
    private final LibrarianRepo librarianRepo;

    @Autowired
    public LibrarianService(LibrarianRepo librarianRepo) {
        this.librarianRepo = librarianRepo;
    }

    public Librarian addLibrarian(Librarian librarian) {
        return librarianRepo.save(librarian);
    }
    public List<Librarian> findAllLibrarians() {
        return librarianRepo.findAll();
    }

    public Librarian updateLibrarian(Librarian librarian) {
        return librarianRepo.save(librarian);
    }

    public Librarian findLibrarianById(Integer id) {
        return librarianRepo.findLibrarianById(id).orElseThrow(() -> new NotFoundException("Not Found"));
    }

    public void deleteLibrarian(Integer id) {
        librarianRepo.deleteLibrarianById(id);
    }
}
