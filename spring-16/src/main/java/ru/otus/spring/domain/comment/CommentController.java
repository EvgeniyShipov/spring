package ru.otus.spring.domain.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String getComments(@PathVariable String id, Model model) {
        Comment comment = service.getComment(id);
        model.addAttribute("comment", comment);
        return "comment";
    }

    @PostMapping("comments")
    public String createComment(String message, String idBook, Model model) {
        Comment comment = service.createComment(message, idBook);
        log.info("Добавлен новый комментарий: " + comment.getMessage());
        model.addAttribute("comment", comment);
        return "comment";
    }

    @DeleteMapping("comments/{id}")
    public String createComment(@PathVariable String id, Model model) {
        Comment comment = service.deleteComment(id);
        log.warning("Комментарий удален: " + comment.getMessage());
        model.addAttribute("comment", comment);
        return "comment";
    }
}
