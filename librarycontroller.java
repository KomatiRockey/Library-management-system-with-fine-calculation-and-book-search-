package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Transaction;
import com.example.library.model.User;
import com.example.library.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class LibraryController {
    private final LibraryService service;

    public LibraryController(LibraryService service) { this.service = service; }

    @GetMapping("/")
    public String index(Model m) {
        m.addAttribute("books", service.listBooks());
        return "index";
    }

    @GetMapping("/add-book")
    public String addBookForm() {
        return "add_book";
    }

    @PostMapping("/add-book")
    public String addBook(@RequestParam String title,
                          @RequestParam String author,
                          @RequestParam int year,
                          @RequestParam String genre) {
        Book b = new Book(title, author, year, genre);
        service.addBook(b);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String title, Model m) {
        if (title != null && !title.isBlank()) {
            Book b = service.findByTitle(title);
            m.addAttribute("book", b);
        }
        return "search_book";
    }

    @GetMapping("/borrow")
    public String borrowForm(Model m) {
        m.addAttribute("books", service.listBooks());
        return "borrow_form";
    }

    @PostMapping("/borrow")
    public String borrow(@RequestParam Long bookId, @RequestParam String username, Model m) {
        // ensure user exists (create if necessary)
        User u = service.addUser(username);
        Transaction t = service.borrowBook(bookId, u.getId());
        if (t == null) {
            m.addAttribute("error", "Could not borrow (book unavailable or invalid).");
            return "borrow_form";
        }
        return "redirect:/";
    }

    @GetMapping("/return")
    public String returnForm(Model m) {
        // for simplicity show all transactions (in real app you'd query)
        // but we will ask user to enter transaction id
        return "return_form";
    }

    @PostMapping("/return")
    public String doReturn(@RequestParam Long txnId, Model m) {
        Transaction t = service.returnBook(txnId);
        if (t == null) {
            m.addAttribute("error", "Transaction not found or already returned.");
            return "return_form";
        }
        m.addAttribute("transaction", t);
        return "return_form";
    }
}

