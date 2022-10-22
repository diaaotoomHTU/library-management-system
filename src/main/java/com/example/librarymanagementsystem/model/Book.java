package com.example.librarymanagementsystem.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;
    private String location;
    private String department;
    private Integer cost;
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date borrowDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    public Book() { }

    public Book(String name, Author author, String location, String department, Integer cost,Member member, Date borrowDate, Date returnDate) {
        this.name = name;
        this.author = author;
        this.location = location;
        this.department = department;
        this.cost = cost;
        this.member = member;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
