package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.CommentRepository;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Log
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository repository;

    @PostFilter("hasPermission(filterObject, 'READ')")
    @GetMapping("comments")
    public List<Comment> getAllComments() {
        return repository.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @GetMapping("comments/{id}")
    public Comment getComment(@PathVariable long id) {
        return repository.findById(id)
                .orElse(null);
    }

    @PreAuthorize("hasPermission(#comment, 'WRITE')")
    @PostMapping("comments")
    public Comment createComment(@RequestBody Comment comment) {
        Comment result = repository.save(comment);
        log.info("Добавлен новый комментарий: " + comment);
        return result;
    }

    @PreAuthorize("hasPermission(#comment, 'WRITE')")
    @PutMapping("comments/{id}")
    public Comment updateComment(@PathVariable long id, @RequestBody Comment comment) {
        Comment result = repository.save(comment);
        log.info("Комментарий изменен: " + comment);
        return result;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long id) {
        repository.deleteById(id);
        log.warning("Комментарий удален, id: " + id);
        return ok("Комментарий удален");
    }
}
