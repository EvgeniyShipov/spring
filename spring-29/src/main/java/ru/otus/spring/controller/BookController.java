package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@Log
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository repository;
    private final CommentRepository comments;

    @GetMapping("books")
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @GetMapping("books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) throws URISyntaxException {
        Book result = repository.save(book);
        log.info("Добавлена новая книга: " + book);
        return created(new URI("/books/" + book.getId()))
                .body(result);
    }

    @PutMapping("books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
        Book result = repository.save(book);
        log.info("Книга изменена: " + book);
        return ok(result);
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        comments.deleteAllByBookId(id);
        repository.deleteById(id);
        log.warning("Книга удалена, id: " + id);
        return ok().build();
    }
}
