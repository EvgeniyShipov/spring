package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@Log
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final LibraryService service;

    @GetMapping("comments")
    public String getAllComments(Model model) {
        List<Comment> comments = service.getAllComments();
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("comments/{id}")
    public String getComment(@PathVariable String id, Model model) {
        Comment comment = service.getComment(id);
        model.addAttribute("comment", comment);
        return "comment";
    }

    @GetMapping("comments/create")
    public String createComment(Comment comment, Model model) {
        List<Book> books = service.getAllBooks();
        model.addAttribute("books", books);
        return "comment_new";
    }

    @PostMapping("comments/create")
    public String createComment(String message, String book, Model model) {
        Comment comment = service.createComment(message, book);
        log.info("Добавлен новый комментарий: " + comment.getMessage());
        model.addAttribute("comments", service.getAllComments());
        return "comments";
    }

    @PostMapping("comments/update/{id}")
    public String updateComment(@PathVariable String id, String message, Model model) {
        Comment comment = service.getComment(id);
        comment.setMessage(message);
        service.updateComment(comment);
        log.info("Комментарий изменен: " + comment.getMessage());
        model.addAttribute("comments", service.getAllComments());
        return "comments";
    }

    @GetMapping("comments/delete/{id}")
    public String deleteComment(@PathVariable String id, Model model) {
        Comment comment = service.deleteComment(id);
        log.warning("Комментарий удален: " + comment.getMessage());
        model.addAttribute("comments", service.getAllComments());
        return "comments";
    }
}
