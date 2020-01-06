package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.JenreRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleLibraryService implements LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final JenreRepository jenreRepository;

    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public Book getBook(long id) {
        return bookRepository.getById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    public List<Jenre> getAllJenre() {
        return jenreRepository.getAll();
    }

    public List<Comment> getAllComments(long idBook) {
        Book book = bookRepository.getById(idBook);
        return book.getComments();
    }

    public Book deleteBook(long id) {
        Book book = bookRepository.getById(id);
        bookRepository.delete(book);
        return book;
    }

    public Book createBook(String title, long idAuthor, long idJenre) {
        Book book = new Book()
                .setTitle(title)
                .setAuthor(authorRepository.getById(idAuthor))
                .setJenre(jenreRepository.getById(idJenre));
        bookRepository.create(book);
        return book;
    }

    public Author createAuthor(String name, String surname, String patronymic) {
        Author author = new Author()
                .setName(name)
                .setSurname(surname)
                .setPatronymic(patronymic);
        return authorRepository.create(author);
    }

    public Jenre createJenre(String name) {
        Jenre jenre = new Jenre().setType(name);
        return jenreRepository.create(jenre);
    }

    public Comment createComment(String message, long idBook) {
        Book book = bookRepository.getById(idBook);
        Comment comment = new Comment().setMessage(message);
        book.addComment(comment);
        bookRepository.update(book);
        return comment;
    }
}
