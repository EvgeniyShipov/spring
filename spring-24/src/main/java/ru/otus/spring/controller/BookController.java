package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;
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
        List<Author> authors = service.getAllAuthors();
        List<Jenre> jenres = service.getAllJenre();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("jenres", jenres);
        return "book";
    }

    @GetMapping("books/create")
    public String createBook(Book book, Model model) {
        List<Author> authors = service.getAllAuthors();
        List<Jenre> jenres = service.getAllJenre();
        model.addAttribute("authors", authors);
        model.addAttribute("jenres", jenres);
        return "book_new";
    }

    @PostMapping("books/create")
    public String createBook(String title, String author, String jenre, Model model) {
        Book book = service.createBook(title, author, jenre);
        log.info(String.format("Добавлена новая книга: %s, автор %s\n", book.getTitle(), book.getAuthor().getFullName()));
        model.addAttribute("books", service.getAllBooks());
        return "redirect:/books";
    }

    @PostMapping("books/update/{id}")
    public String updateBook(@PathVariable String id, String title, String author, String jenre, Model model) {
        Book book = service.getBook(id);
        book.setTitle(title);
        book.setAuthor(service.getAuthor(author));
        book.setJenre(service.getJenre(jenre));
        service.updateBook(book);
        log.info("Книга изменена: " + book.getTitle());
        model.addAttribute("books", service.getAllBooks());
        return "redirect:/books";
    }

    @PostMapping("books/delete/{id}")
    public String deleteBook(@PathVariable String id, Model model) {
        Book book = service.deleteBook(id);
        log.warning(String.format("Книга удалена: %s, автор %s\n", book.getTitle(), book.getAuthor().getFullName()));
        model.addAttribute("books", service.getAllBooks());
        return "redirect:/books";
    }
}
