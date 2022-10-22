package com.example.librarymanagementsystem.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Book> books;

    public Set<Book> getBooks() {
        return books;
    }

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
