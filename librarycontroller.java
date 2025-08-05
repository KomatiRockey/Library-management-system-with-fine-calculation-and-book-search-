package com.library.controller;

import com.library.model.*;
import com.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class LibraryController {

    @Autowired private BookRepository bookRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private TransactionRepository transRepo;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookRepo.findAll());
        return "index";
    }

    @GetMapping("/add_book")
    public String addBookForm() {
        return "add_book";
    }

    @PostMapping("/add_book")
    public String saveBook(@RequestParam String title, @RequestParam String author,
                           @RequestParam int year, @RequestParam String genre) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
        book.setGenre(genre);
        book.setAvailable(true);
        bookRepo.save(book);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam String title, Model model) {
        Book book = bookRepo.findByTitle(title);
        model.addAttribute("book", book);
        return "search_book";
    }

    @GetMapping("/borrow/{bookId}")
    public String borrowBook(@PathVariable Long bookId, @RequestParam Long userId) {
        Book book = bookRepo.findById(bookId).orElse(null);
        User user = userRepo.findById(userId).orElse(null);
        if (book != null && user != null && book.isAvailable()) {
            book.setAvailable(false);
            bookRepo.save(book);

            Transaction txn = new Transaction();
            txn.setBook(book);
            txn.setUser(user);
            txn.setBorrowDate(new Date());
            transRepo.save(txn);
        }
        return "redirect:/";
    }
}
