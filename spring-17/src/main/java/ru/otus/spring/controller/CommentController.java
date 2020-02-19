package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.CommentRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@Log
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository repository;

    @GetMapping("comments")
    public List<Comment> getAllComments() {
        return repository.findAll();
    }

    @GetMapping("comments/{id}")
    public ResponseEntity<?> getComment(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @PostMapping("comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) throws URISyntaxException {
        Comment result = repository.save(comment);
        log.info("Добавлен новый комментарий: " + comment);
        return created(new URI("/comments/" + comment.getId()))
                .body(result);
    }

    @PutMapping("comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody Comment comment) {
        Comment result = repository.save(comment);
        log.info("Комментарий изменен: " + comment);
        return ok(result);
    }

    @DeleteMapping("comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        repository.deleteById(Long.valueOf(id));
        log.warning("Комментарий удален, id: " + id);
        return ok().build();
    }
}
