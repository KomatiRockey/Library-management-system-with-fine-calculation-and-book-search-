package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Transaction;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TransactionRepository;
import com.example.library.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final BookRepository bookRepo;
    private final UserRepository userRepo;
    private final TransactionRepository txnRepo;

    // Define allowed days (e.g., 14 days) and fine per day
    private final int allowedDays = 14;
    private final int finePerDay = 5; // currency units per late day

    public LibraryService(BookRepository bookRepo, UserRepository userRepo, TransactionRepository txnRepo) {
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
        this.txnRepo = txnRepo;
    }

    public List<Book> listBooks() { return bookRepo.findAll(); }

    public Book addBook(Book b) { return bookRepo.save(b); }

    public Book findByTitle(String title) { return bookRepo.findFirstByTitleIgnoreCase(title); }

    public Optional<Book> getBook(Long id) { return bookRepo.findById(id); }

    public User addUser(String name) {
        User u = new User(name);
        return userRepo.save(u);
    }

    public Optional<User> getUser(Long id) { return userRepo.findById(id); }

    public Transaction borrowBook(Long bookId, Long userId) {
        Optional<Book> bOpt = bookRepo.findById(bookId);
        Optional<User> uOpt = userRepo.findById(userId);
        if (bOpt.isEmpty() || uOpt.isEmpty()) return null;
        Book b = bOpt.get();
        if (!b.isAvailable()) return null;
        b.setAvailable(false);
        bookRepo.save(b);

        Transaction t = new Transaction();
        t.setBook(b);
        t.setUser(uOpt.get());
        t.setBorrowDate(LocalDate.now());
        t.setFine(0);
        return txnRepo.save(t);
    }

    public Transaction returnBook(Long txnId) {
        Optional<Transaction> tOpt = txnRepo.findById(txnId);
        if (tOpt.isEmpty()) return null;
        Transaction t = tOpt.get();
        if (t.getReturnDate() != null) return t; // already returned

        LocalDate today = LocalDate.now();
        t.setReturnDate(today);

        long days = ChronoUnit.DAYS.between(t.getBorrowDate(), today);
        long lateDays = Math.max(0, days - allowedDays);
        int fine = (int) (lateDays * finePerDay);
        t.setFine(fine);

        // mark book available
        Book book = t.getBook();
        book.setAvailable(true);
        bookRepo.save(book);

        return txnRepo.save(t);
    }

    public List<Transaction> transactionsForUser(Long userId) {
        return txnRepo.findByUserId(userId);
    }
}
