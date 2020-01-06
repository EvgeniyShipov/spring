package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.service.LibraryService;

@ShellComponent
@RequiredArgsConstructor
public class GameCommand {
    private final LibraryService libraryService;

    @ShellMethod(value = "get all books", key = {"get-all-books", "gab"})
    public void getAllBooks() {
        libraryService.getAllBooks().forEach(book ->
                System.out.printf("%s, автор %s\n", book.getTitle(), book.getAuthor().getFullName()));
    }

    @ShellMethod(value = "get all authors", key = {"get-all-authors", "gaa"})
    public void getAllAuthors() {
        libraryService.getAllAuthors().forEach(author ->
                System.out.println(author.getFullName()));
    }

    @ShellMethod(value = "get all jenre", key = {"get-all-jenre", "gaj"})
    public void getAllJenre() {
        libraryService.getAllJenre().forEach(jenre ->
                System.out.println(jenre.getType()));
    }

    @ShellMethod(value = "get all comment", key = {"get-all-comment", "gac"})
    public void getAllComments(@ShellOption long idBook) {
        libraryService.getAllComments(idBook).forEach(comment ->
                System.out.println(comment.getMessage()));
    }

    @ShellMethod(value = "delete book", key = {"delete-book", "db"})
    public void deleteBook(@ShellOption long id) {
        Book book = libraryService.deleteBook(id);
        System.out.printf("Книга удалена: %s, автор %s\n", book.getTitle(), book.getAuthor().getFullName());
    }

    @ShellMethod(value = "create book", key = {"create-book", "cb"})
    public void createBook(@ShellOption String title,
                           @ShellOption long idAuthor,
                           @ShellOption long idJenre) {
        Book book = libraryService.createBook(title, idAuthor, idJenre);
        System.out.printf("Добавлена новая книга: %s, автор %s\n", book.getTitle(), book.getAuthor().getFullName());
    }

    @ShellMethod(value = "create author", key = {"create-author", "ca"})
    public void createAuthor(@ShellOption String name,
                             @ShellOption String surname,
                             @ShellOption String patronymic) {
        Author author = libraryService.createAuthor(name, surname, patronymic);
        System.out.println("Добавлен новый автор: " + author.getFullName());
    }

    @ShellMethod(value = "create jenre", key = {"create-jenre", "cj"})
    public void createAuthor(@ShellOption String name) {
        Jenre jenre = libraryService.createJenre(name);
        System.out.println("Добавлен новый жанр: " + jenre.getType());
    }

    @ShellMethod(value = "create comment", key = {"create-comment", "cc"})
    public void createComment(@ShellOption String message, @ShellOption long idBook) {
        Comment comment = libraryService.createComment(message, idBook);
        System.out.println("Добавлен новый комментарий: " + comment.getMessage());
    }
}
