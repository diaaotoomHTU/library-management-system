package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.exceptions.NotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Librarian;
import com.example.librarymanagementsystem.model.Member;
import com.example.librarymanagementsystem.service.AuthorService;
import com.example.librarymanagementsystem.service.BookService;
import com.example.librarymanagementsystem.service.LibrarianService;
import com.example.librarymanagementsystem.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class BorrowedResource {
    private final BookService bookService;
    private final MemberService memberService;
    private final LibrarianService librarianService;

    public BorrowedResource(BookService bookService, MemberService memberService, LibrarianService librarianService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.librarianService = librarianService;
    }

    public boolean validateLogin(HttpSession httpSession) {
        try {
            for (Librarian currentLibrarian: librarianService.findAllLibrarians()) {
                if (httpSession.getAttribute("username").equals(currentLibrarian.getUsername()) && httpSession.getAttribute("password").equals(currentLibrarian.getPassword())) {
                    return true;
                }
            }
        } catch (Exception e){

        }
        return false;
    }

    @GetMapping("/borrowed/{id}")
    public ModelAndView getAllBooks(@PathVariable("id") Integer id, HttpSession httpSession){
        if (!validateLogin(httpSession)) {
            return new ModelAndView("redirect:/index");
        }
        ModelAndView mav = new ModelAndView("borrowed");
        Member member = memberService.findMemberById(id);
        Book bookToLoan = new Book();
        Book loanToUpdate = new Book();
        Book loanToDelete = new Book();
        List<Book> books = bookService.findAllBooks();
        List<Book> memberBooks = new ArrayList<Book>();
        for (Book book: books) {
            if (book.getMember() != null && book.getMember().getId() == id) {
                memberBooks.add(book);
            }
        }
        mav.addObject("member", member);
        mav.addObject("books", memberBooks);
        mav.addObject("bookToLoan", bookToLoan);
        mav.addObject("loanToUpdate", loanToUpdate);
        mav.addObject("loanToDelete", loanToDelete);
        return mav;
    }

    /*@GetMapping("/find/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id){
        Book book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }*/


    @PostMapping("/loanBook/{id}")
    public String loanBook(@ModelAttribute Book book, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Book bookToLoan;
        try {
            bookToLoan = bookService.findBookById(book.getId());
        } catch (NotFoundException exception) {
            bookToLoan = null;
        }
        if (bookToLoan == null || bookToLoan.getMember() != null) {
            redirectAttributes.addFlashAttribute("success", false);
            return "redirect:/borrowed/{id}";
        }
        bookToLoan.setMember(memberService.findMemberById(id));
        Date borrowDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(borrowDate);
        c.add(Calendar.DATE, 35);
        Date returnDate = c.getTime();
        bookToLoan.setBorrowDate(borrowDate);
        bookToLoan.setReturnDate(returnDate);
        bookService.updateBook(bookToLoan);
        return "redirect:/borrowed/{id}";
    }

    @PostMapping("/deleteLoan/{id}")
    public String deleteLoan(@ModelAttribute Book book, @PathVariable("id") Integer id) {
        Book loanToDelete = bookService.findBookById(book.getId());
        loanToDelete.setMember(null);
        loanToDelete.setBorrowDate(null);
        loanToDelete.setReturnDate(null);
        bookService.updateBook(loanToDelete);
        return "redirect:/borrowed/{id}";
    }

    @PostMapping("/updateLoan/{id}")
    public String updateLoan(@ModelAttribute Book book, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Book loanToUpdate = bookService.findBookById(book.getId());
        if (book.getBorrowDate().after(book.getReturnDate())) {
            redirectAttributes.addFlashAttribute("dateSuccess", false);
            return "redirect:/borrowed/{id}";
        }
        loanToUpdate.setBorrowDate(book.getBorrowDate());
        loanToUpdate.setReturnDate(book.getReturnDate());
        bookService.updateBook(loanToUpdate);
        return "redirect:/borrowed/{id}";
    }

    /*
    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute Book book) {
        List<Book> books = bookService.findAllBooks();
        for (Book currentBook: books) {
            if (currentBook.getId().equals(book.getId())) {
                book.setAuthor(currentBook.getAuthor());
                book.setBorrowDate(currentBook.getBorrowDate());
                book.setReturnDate(currentBook.getReturnDate());
                book.setMember(currentBook.getMember());
            }
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @Transactional
    @PostMapping("/deleteBook")
    public String deleteBook(@ModelAttribute Book book) {
        System.out.println(book.getId());
        bookService.deleteBook(book.getId());
        return "redirect:/books";
    }*/
}
