package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@Log
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository repository;
    private final BookRepository books;

    @GetMapping("authors")
    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    @GetMapping("authors/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @PostMapping("authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) throws URISyntaxException {
        Author result = repository.save(author);
        log.info("Добавлен новый автор: " + result);
        return created(new URI("/authors/" + result.getId()))
                .body(result);
    }

    @PutMapping("authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable String id, @RequestBody Author author) {
        Author result = repository.save(author);
        log.info("Автор изменен: " + result);
        return ok(result);
    }

    @DeleteMapping("authors/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable String id) {
        if (!books.existsByAuthorId(id)) {
            repository.deleteById(Long.valueOf(id));
            log.warning("Автор удален, id: " + id);
            return ok().build();
        } else {
            return badRequest().body("Нельзя удалить автора, оставив книги");
        }
    }
}
