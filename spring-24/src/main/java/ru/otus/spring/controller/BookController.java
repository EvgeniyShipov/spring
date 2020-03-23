package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Log
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository repository;
    private final CommentRepository comments;

    @PostFilter("hasPermission(filterObject, 'READ')")
    @GetMapping("books")
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @GetMapping("books/{id}")
    public Book getBook(@PathVariable long id) {
        return repository.findById(id)
                .orElse(null);
    }

    @PreAuthorize("hasPermission(#book, 'WRITE')")
    @PostMapping("books")
    public Book createBook(@RequestBody Book book) {
        Book result = repository.save(book);
        log.info("Добавлена новая книга: " + book);
        return result;
    }

    @PreAuthorize("hasPermission(#book, 'WRITE')")
    @PutMapping("books/{id}")
    public Book updateBook(@PathVariable long id, @RequestBody Book book) {
        Book result = repository.save(book);
        log.info("Книга изменена: " + book);
        return result;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        comments.deleteAllByBookId(id);
        repository.deleteById(id);
        log.warning("Книга удалена, id: " + id);
        return ok("Книга удалена");
    }
}
