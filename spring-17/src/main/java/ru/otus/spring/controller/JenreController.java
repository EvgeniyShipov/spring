package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.JenreRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.springframework.http.ResponseEntity.*;

@Log
@RestController
@RequiredArgsConstructor
public class JenreController {

    private final JenreRepository repository;
    private final BookRepository books;

    @GetMapping("jenres")
    public Collection<Jenre> getAllJenre() {
        return repository.findAll();
    }

    @GetMapping("jenres/{id}")
    public ResponseEntity<?> getJenre(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @PostMapping("jenres")
    public ResponseEntity<Jenre> createJenre(@RequestBody Jenre jenre) throws URISyntaxException {
        Jenre result = repository.save(jenre);
        log.info("Добавлен новый жанр: " + result);
        return created(new URI("/jenres/" + result.getId()))
                .body(result);
    }

    @PostMapping("jenres/{id}")
    public ResponseEntity<Jenre> updateJenre(@PathVariable String id, @RequestBody Jenre jenre) {
        Jenre result = repository.save(jenre);
        log.info("Жанр изменен: " + jenre);
        return ok(result);
    }

    @DeleteMapping("jenres/{id}")
    public ResponseEntity<?> deleteJenre(@PathVariable String id) {
        if (!books.existsByJenreId(id)) {
            repository.deleteById(id);
            log.warning("Жанр удален, id: " + id);
            return ok().build();
        } else {
            return badRequest().body("Нельзя удалить жанр, оставив книги");
        }
    }
}
