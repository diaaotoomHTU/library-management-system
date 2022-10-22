package com.example.librarymanagementsystem;

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

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
public class BookResource {
    private final BookService bookService;
    private final AuthorService authorService;
    private final MemberService memberService;
    private final LibrarianService librarianService;

    public BookResource(BookService bookService, AuthorService authorService, MemberService memberService, LibrarianService librarianService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.memberService = memberService;
        this.librarianService = librarianService;
    }

    @GetMapping("/books")
    public ModelAndView getFirstFiveBooks(String bookname, Integer bookid, String authorname, Integer authorid, HttpSession httpSession){
        if (!validateLogin(httpSession)) {
            return new ModelAndView("redirect:/index");
        }
        ModelAndView mav = new ModelAndView("books");
        List<Book> books = new ArrayList<Book>();
        LinkedHashSet<Book> bookSearch = new LinkedHashSet<Book>();
        LinkedHashSet<Book> booksById = new LinkedHashSet<Book>();
        LinkedHashSet<Book> booksByName = new LinkedHashSet<Book>();
        if (bookname != null && !bookname.equals("")) {
            booksByName.addAll(bookService.findByBookName(bookname));
        } else {
            booksByName.addAll(bookService.findAllBooks());
        }
        if (bookid != null) {
            booksById.addAll(bookService.findByBookId(bookid));
        } else {
            booksById.addAll(bookService.findAllBooks());
        }
        for (Book book: booksById) {
            if (booksByName.contains(book)) {
                bookSearch.add(book);
            }
        }
        LinkedHashSet<Author> authorSearch = new LinkedHashSet<Author>();
        LinkedHashSet<Author> authorById = new LinkedHashSet<Author>();
        LinkedHashSet<Author> authorByName = new LinkedHashSet<Author>();
        if (authorname != null && !authorname.equals("")) {
            authorByName.addAll(authorService.findByAuthorName(authorname));
        } else {
            authorByName.addAll(authorService.findAllAuthors());
        }
        if (authorid != null) {
            authorById.addAll(authorService.findByAuthorId(authorid));
        } else {
            authorById.addAll(authorService.findAllAuthors());
        }
        for (Author author: authorById) {
            if (authorByName.contains(author)) {
                authorSearch.add(author);
            }
        }
        for (Book book: bookSearch) {
            if (authorSearch.contains(book.getAuthor())) {
                books.add(book);
            }
        }
        List<Book> booksToReturn = new ArrayList<Book>();
        List<Boolean> pageButtons = new ArrayList<Boolean>();
        pageButtons.add(true);
        for (int i = 5; i < books.size(); i += 5) {
            pageButtons.add(false);
        }
        for (int i = 0; i < 5 && i < books.size(); ++i) {
            booksToReturn.add(books.get(i));
        }
        Book newBook = new Book();
        Book updateBook = new Book();
        Book deleteBook = new Book();
        mav.addObject("books", books);
        mav.addObject("newBook", newBook);
        mav.addObject("updateBook", updateBook);
        mav.addObject("deleteBook", deleteBook);
        mav.addObject("booksToReturn", booksToReturn);
        mav.addObject("pageButtons", pageButtons);
        return mav;
    }


    @GetMapping("/books/{page}")
    public ModelAndView getAllBooks(@PathVariable("page") Integer page, String bookname, Integer bookid, String authorname, Integer authorid){
        ModelAndView mav = new ModelAndView("books");
        List<Book> books = new ArrayList<Book>();
        LinkedHashSet<Book> bookSearch = new LinkedHashSet<Book>();
        LinkedHashSet<Book> booksById = new LinkedHashSet<Book>();
        LinkedHashSet<Book> booksByName = new LinkedHashSet<Book>();
        if (bookname != null && !bookname.equals("")) {
            booksByName.addAll(bookService.findByBookName(bookname));
        } else {
            booksByName.addAll(bookService.findAllBooks());
        }
        if (bookid != null) {
            booksById.addAll(bookService.findByBookId(bookid));
        } else {
            booksById.addAll(bookService.findAllBooks());
        }
        for (Book book: booksById) {
            if (booksByName.contains(book)) {
                bookSearch.add(book);
            }
        }
        LinkedHashSet<Author> authorSearch = new LinkedHashSet<Author>();
        LinkedHashSet<Author> authorById = new LinkedHashSet<Author>();
        LinkedHashSet<Author> authorByName = new LinkedHashSet<Author>();
        if (authorname != null && !authorname.equals("")) {
            authorByName.addAll(authorService.findByAuthorName(authorname));
        } else {
            authorByName.addAll(authorService.findAllAuthors());
        }
        if (authorid != null) {
            authorById.addAll(authorService.findByAuthorId(authorid));
        } else {
            authorById.addAll(authorService.findAllAuthors());
        }
        for (Author author: authorById) {
            if (authorByName.contains(author)) {
                authorSearch.add(author);
            }
        }
        for (Book book: bookSearch) {
            if (authorSearch.contains(book.getAuthor())) {
                books.add(book);
            }
        }
        List<Book> booksToReturn = new ArrayList<Book>();
        page *= 5;
        List<Boolean> pageButtons = new ArrayList<Boolean>();
        for (int i = 5; i < books.size() + 5; i += 5) {
            if (i == page) {
                pageButtons.add(true);
            } else {
                pageButtons.add(false);
            }
        }
        for (int i = page - 5; i < page && i < books.size(); ++i) {
            booksToReturn.add(books.get(i));
        }
        Book newBook = new Book();
        Book updateBook = new Book();
        Book deleteBook = new Book();
        mav.addObject("books", books);
        mav.addObject("newBook", newBook);
        mav.addObject("updateBook", updateBook);
        mav.addObject("deleteBook", deleteBook);
        mav.addObject("booksToReturn", booksToReturn);
        mav.addObject("pageButtons", pageButtons);
        return mav;
    }

    @GetMapping("/dashboard")
    public ModelAndView getDashboard(HttpSession httpSession){
        if (!validateLogin(httpSession)) {
            return new ModelAndView("redirect:/index");
        }
        ModelAndView mav = new ModelAndView("dashboard");
        List<Book> allBooks = bookService.findAllBooks();
        List<Book> books = new ArrayList<Book>();
        List<Book> borrowedBooks = new ArrayList<Book>();
        int[] statistics = new int[3];
        for (int i = allBooks.size() - 1; i > -1; --i) {
            if (allBooks.get(i).getMember() != null) {
                if (books.size() < 6) {
                    books.add(allBooks.get(i));
                }
                borrowedBooks.add(allBooks.get(i));
            }
        }
        statistics[0] = allBooks.size();
        statistics[1] = memberService.findAllMembers().size();
        statistics[2] = borrowedBooks.size();
        mav.addObject("books", books);
        mav.addObject("statistics", statistics);
        return mav;
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

    @GetMapping("/index")
    public ModelAndView getIndex(HttpSession httpSession){
        if (validateLogin(httpSession)) {
            return new ModelAndView("redirect:/dashboard");
        }
        ModelAndView mav = new ModelAndView("index");
        Librarian librarian = new Librarian();
        mav.addObject("librarian", librarian);
        return mav;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        System.out.println(httpSession.getAttribute("username"));
        httpSession.invalidate();
        return "redirect:/index";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute Librarian librarian, HttpSession httpSession) {
        for (Librarian currentLibrarian: librarianService.findAllLibrarians()) {
            if (librarian.getUsername().equals(currentLibrarian.getUsername()) && librarian.getPassword().equals(currentLibrarian.getPassword())) {
                httpSession.setAttribute("username", librarian.getUsername());
                httpSession.setAttribute("password", librarian.getPassword());
                return "redirect:/dashboard";
            }
        }
        return "redirect:/index";
    }

    /*@GetMapping("/find/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id){
        Book book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }*/

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book book) {
        List<Author> authors = authorService.findAllAuthors();
        boolean newAuthor = true;
        for (Author currentAuthor: authors) {
            if (currentAuthor.getName().equals(book.getAuthor().getName())) {
                book.setAuthor(currentAuthor);
                currentAuthor.getBooks().add(book);
                authorService.updateAuthor(currentAuthor);
                newAuthor = false;
            }
        }
        if (newAuthor) {
            authorService.addAuthor(book.getAuthor());
        }
        bookService.addBook(book);
        return "redirect:/books";
    }


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
    }
}
