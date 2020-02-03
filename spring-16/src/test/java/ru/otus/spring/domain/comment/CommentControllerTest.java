package ru.otus.spring.domain.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.otus.spring.service.LibraryService;

import java.util.Collections;

import static org.mockito.Mockito.*;

class CommentControllerTest {

    private LibraryService service;
    private CommentController controller;
    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        service = mock(LibraryService.class);
        controller = new CommentController(service);
    }

    @Test
    void getAllComments() {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.getAllComments()).thenReturn(Collections.singletonList(comment));

        controller.getAllComments(model);

        verify(service).getAllComments();
    }

    @Test
    void getComments() {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.getComment(comment.getId())).thenReturn(comment);

        controller.getComment(comment.getId(), model);

        verify(service).getComment(comment.getId());
    }

    @Test
    void createComment() {
        String bookId = "1";
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.createComment(comment.getMessage(), bookId)).thenReturn(comment);

        controller.createComment(comment.getMessage(), bookId, model);

        verify(service).createComment(comment.getMessage(), bookId);
    }

    @Test
    void deleteComment() {
        Comment comment = new Comment().setId("1").setMessage("message");
        when(service.deleteComment(comment.getId())).thenReturn(comment);

        controller.deleteComment(comment.getId(), model);

        verify(service).deleteComment(comment.getId());
    }
}