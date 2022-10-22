package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AuthorResource {
    private final AuthorService authorService;

    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public ModelAndView getAllAuthors(){
        ModelAndView mav = new ModelAndView("members");
        List<Author> authors = authorService.findAllAuthors();
        Author newAuthor = new Author();
        Author updateAuthor = new Author();
        Author deleteAuthor = new Author();
        mav.addObject("authors", authors);
        mav.addObject("newAuthor", newAuthor);
        mav.addObject("updateAuthor", updateAuthor);
        mav.addObject("deleteAuthor", deleteAuthor);
        return mav;
    }

    /*@GetMapping("/find/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Integer id){
        Author author = authorService.findAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }*/

    @PostMapping("/addAuthor")
    public String addAuthor(@ModelAttribute Author author) {
        authorService.addAuthor(author);
        return "redirect:/authors";
    }

    @PostMapping("/updateAuthor")
    public String updateAuthor(@ModelAttribute Author author) {
        authorService.updateAuthor(author);
        return "redirect:/authors";
    }

    @Transactional
    @PostMapping("/deleteAuthor")
    public String deleteAuthor(@ModelAttribute Author author) {
        authorService.deleteAuthor(author.getId());
        return "redirect:/authors";
    }
}
