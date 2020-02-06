package ru.otus.spring.domain.book;

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
public class BookController {

    private final LibraryService service;

    @GetMapping("books")
    public String getAllBooks(Model model) {
        List<Book> books = service.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("books/{id}")
    public String getBook(@PathVariable String id, Model model) {
        Book book = service.getBook(id);
        model.addAttribute("book", book);
        return "book";
    }

    @PostMapping("books")
    public String createBook(String title, String idAuthor, String idJenre, Model model) {
        Book book = service.createBook(title, idAuthor, idJenre);
        log.info(String.format("Добавлена новая книга: %s, автор %s\n", book.getTitle(), book.getAuthor().getFullName()));
        model.addAttribute("books", service.getAllBooks());
        return "books";
    }

    @PostMapping("books/{id}")
    public String updateAuthor(@PathVariable String id, String title, Model model) {
        Book book = service.getBook(id);
        book.setTitle(title);
        service.updateBook(book);
        log.info("Книга изменена: " + book.getTitle());
        model.addAttribute("books", service.getAllBooks());
        return "books";
    }

    @DeleteMapping("books/{id}")
    public String deleteBook(@PathVariable String id, Model model) {
        Book book = service.deleteBook(id);
        log.warning(String.format("Книга удалена: %s, автор %s\n", book.getTitle(), book.getAuthor().getFullName()));
        model.addAttribute("books", service.getAllBooks());
        return "books";
    }
}
