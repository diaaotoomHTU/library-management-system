package com.example.librarymanagementsystem.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String not_found) {
        super(not_found);
    }
}
