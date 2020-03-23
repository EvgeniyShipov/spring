package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.JenreRepository;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Log
@RestController
@RequiredArgsConstructor
public class JenreController {

    private final JenreRepository repository;
    private final BookRepository books;

    @PostFilter("hasPermission(filterObject, 'READ')")
    @GetMapping("jenres")
    public Collection<Jenre> getAllJenre() {
        return repository.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @GetMapping("jenres/{id}")
    public Jenre getJenre(@PathVariable long id) {
        return repository.findById(id)
                .orElse(null);
    }

    @PreAuthorize("hasPermission(#jenre, 'WRITE')")
    @PostMapping("jenres")
    public Jenre createJenre(@RequestBody Jenre jenre) {
        Jenre result = repository.save(jenre);
        log.info("Добавлен новый жанр: " + result);
        return result;
    }

    @PreAuthorize("hasPermission(#jenre, 'WRITE')")
    @PutMapping("jenres/{id}")
    public Jenre updateJenre(@PathVariable long id, @RequestBody Jenre jenre) {
        Jenre result = repository.save(jenre);
        log.info("Жанр изменен: " + jenre);
        return result;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("jenres/{id}")
    public ResponseEntity<String> deleteJenre(@PathVariable long id) {
        if (!books.existsByJenreId(id)) {
            repository.deleteById(id);
            log.warning("Жанр удален, id: " + id);
            return ok("Жанр удален");
        } else {
            return badRequest().body("Нельзя удалить жанр, оставив книги");
        }
    }
}
