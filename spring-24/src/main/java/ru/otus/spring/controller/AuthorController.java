package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;

import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Log
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository repository;
    private final BookRepository books;

    @PostFilter("hasPermission(filterObject, 'READ')")
    @GetMapping("authors")
    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @GetMapping("authors/{id}")
    public Author getAuthor(@PathVariable long id) {
        return repository.findById(id)
                .orElse(null);
    }

    @PreAuthorize("hasPermission(#author, 'WRITE')")
    @PostMapping("authors")
    public Author createAuthor(@RequestBody Author author) {
        Author result = repository.save(author);
        log.info("Добавлен новый автор: " + result);
        return result;
    }

    @PreAuthorize("hasPermission(#author, 'WRITE')")
    @PutMapping("authors/{id}")
    public Author updateAuthor(@PathVariable long id, @RequestBody Author author) {
        Author result = repository.save(author);
        log.info("Автор изменен: " + result);
        return result;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("authors/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable long id) {
        if (!books.existsByAuthorId(id)) {
            repository.deleteById(id);
            log.warning("Автор удален, id: " + id);
            return ok("Автор удален");
        } else {
            return badRequest().body("Нельзя удалить автора, оставив книги");
        }
    }
}
