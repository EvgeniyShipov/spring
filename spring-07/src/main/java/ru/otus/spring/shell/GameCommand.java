package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.service.LibraryService;

@ShellComponent
@RequiredArgsConstructor
public class GameCommand {
    private final LibraryService libraryService;

    @ShellMethod(value = "get all books", key = {"get-all-books", "gab"})
    public void getAllBooks() {
        System.out.println(libraryService.getAllBooks());
    }

    @ShellMethod(value = "get all authors", key = {"get-all-authors", "gaa"})
    public void getAllAuthors() {
        System.out.println(libraryService.getAllAuthors());
    }

    @ShellMethod(value = "get all jenre", key = {"get-all-jenre", "gaj"})
    public void getAllJenre() {
        System.out.println(libraryService.getAllJenre());
    }

    @ShellMethod(value = "delete book", key = {"delete-book", "db"})
    public void deleteBook(@ShellOption int id) {
        Book book = libraryService.deleteBook(id);
        System.out.println("Книга удалена: " + book);
    }

    @ShellMethod(value = "create book", key = {"create-book", "cb"})
    public void createBook(@ShellOption String title,
                           @ShellOption int idAuthor,
                           @ShellOption Jenre jenre) {
        Book book = libraryService.createBook(title, idAuthor, jenre);
        System.out.println("Добавлена новая книга: " + book);
    }

    @ShellMethod(value = "create author", key = {"create-author", "ca"})
    public void createAuthor(@ShellOption String name,
                             @ShellOption String surname,
                             @ShellOption String patronymic) {
        Author author = libraryService.createAuthor(name, surname, patronymic);
        System.out.println("Добавлен новый автор: " + author);
    }
}
