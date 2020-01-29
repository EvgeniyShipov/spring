package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleLibraryService implements LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final JenreRepository jenreRepository;
    private final CommentRepository commentRepository;

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book getBook(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Книга не найдена"));
    }

    @Override
    public List<Book> getBookByAuthor(String idAuthor) {
        Author author = authorRepository.findById(idAuthor)
                .orElseThrow(() -> new NullPointerException("Автор не найден"));
        return bookRepository.findByAuthor(author);
    }

    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public List<Book> getBookByJenre(String idJenre) {
        Jenre jenre = jenreRepository.findById(idJenre)
                .orElseThrow(() -> new NullPointerException("Жанр не найден"));
        return bookRepository.findByJenre(jenre);
    }

    public List<Jenre> getAllJenre() {
        return (List<Jenre>) jenreRepository.findAll();
    }

    public List<Comment> getCommentsByBookId(String idBook) {
        return commentRepository.findByBookId(idBook);
    }

    public List<Comment> getAllComments() {
        return (List<Comment>) commentRepository.findAll();
    }

    @Transactional
    public Book deleteBook(String id) {
        commentRepository.deleteAllByBookId(id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Книга не найдена"));
        bookRepository.delete(book);
        return book;
    }

    @Transactional
    public Book createBook(String title, String idAuthor, String idJenre) {
        Book book = new Book()
                .setTitle(title)
                .setAuthor(authorRepository.findById(idAuthor)
                        .orElseThrow(() -> new NullPointerException("Автор не найден")))
                .setJenre(jenreRepository.findById(idJenre)
                        .orElseThrow(() -> new NullPointerException("Жанр не найден")));
        bookRepository.save(book);
        return book;
    }

    public Author createAuthor(String name, String surname, String patronymic) {
        Author author = new Author()
                .setName(name)
                .setSurname(surname)
                .setPatronymic(patronymic);
        return authorRepository.save(author);
    }

    public Jenre createJenre(String name) {
        Jenre jenre = new Jenre().setType(name);
        return jenreRepository.save(jenre);
    }

    @Transactional
    public Comment createComment(String message, String idBook) {
        Book book = bookRepository.findById(idBook)
                .orElseThrow(() -> new NullPointerException("Книга не найдена"));
        Comment comment = new Comment()
                .setMessage(message)
                .setBook(book);
        commentRepository.save(comment);
        return comment;
    }
}
