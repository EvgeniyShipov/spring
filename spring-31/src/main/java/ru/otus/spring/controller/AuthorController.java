package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@Log
@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final LibraryService service;

    @GetMapping("authors")
    public String getAllAuthors(Model model) {
        List<Author> authors = service.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("authors/{id}")
    public String getAuthor(@PathVariable long id, Model model) {
        Author author = service.getAuthor(id);
        model.addAttribute("author", author);
        return "author";
    }

    @GetMapping("authors/create")
    public String createAuthor(Author author, Model model) {
        return "author_new";
    }

    @PostMapping("authors/create")
    public String createAuthor(String name, String surname, String patronymic, Model model) {
        Author author = service.createAuthor(name, surname, patronymic);
        log.info("Добавлен новый автор: " + author.getFullName());
        model.addAttribute("authors", service.getAllAuthors());
        return "redirect:/authors";
    }

    @PostMapping("authors/update/{id}")
    public String updateAuthor(@PathVariable long id, String name, String surname, String patronymic, Model model) {
        Author author = service.getAuthor(id);
        author.setName(name).setSurname(surname).setPatronymic(patronymic);
        service.updateAuthor(author);
        log.info("Автор изменен: " + author.getFullName());
        model.addAttribute("authors", service.getAllAuthors());
        return "redirect:/authors";
    }

    @PostMapping("authors/delete/{id}")
    public String deleteAuthor(@PathVariable long id, Model model) {
        Author author = service.deleteAuthor(id);
        log.warning("Автор удален: " + author.getFullName());
        model.addAttribute("authors", service.getAllAuthors());
        return "redirect:/authors";
    }
}
