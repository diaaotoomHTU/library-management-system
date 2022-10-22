package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.model.Librarian;
import com.example.librarymanagementsystem.service.LibrarianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LibrarianResource {
    private final LibrarianService librarianService;

    public LibrarianResource(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @GetMapping("/librarians")
    public ModelAndView getAllLibrarians(){
        ModelAndView mav = new ModelAndView("librarians");
        List<Librarian> librarians = librarianService.findAllLibrarians();
        Librarian newLibrarian = new Librarian();
        Librarian updateLibrarian = new Librarian();
        Librarian deleteLibrarian = new Librarian();
        mav.addObject("librarians", librarians);
        mav.addObject("newLibrarian", newLibrarian);
        mav.addObject("updateLibrarian", updateLibrarian);
        mav.addObject("deleteLibrarian", deleteLibrarian);
        return mav;
    }

    /*@GetMapping("/find/{id}")
    public ResponseEntity<Librarian> getLibrarianById(@PathVariable("id") Integer id){
        Librarian librarian = librarianService.findLibrarianById(id);
        return new ResponseEntity<>(librarian, HttpStatus.OK);
    }*/

    @PostMapping("/addLibrarian")
    public String addLibrarian(@ModelAttribute Librarian librarian) {
        librarianService.addLibrarian(librarian);
        return "redirect:/librarians";
    }

    @PostMapping("/updateLibrarian")
    public String updateLibrarian(@ModelAttribute Librarian librarian) {
        librarianService.updateLibrarian(librarian);
        return "redirect:/librarians";
    }

    @Transactional
    @PostMapping("/deleteLibrarian")
    public String deleteLibrarian(@ModelAttribute Librarian librarian) {
        librarianService.deleteLibrarian(librarian.getId());
        return "redirect:/librarians";
    }
}
