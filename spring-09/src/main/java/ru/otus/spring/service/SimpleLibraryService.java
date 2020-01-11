package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public Book getBook(long id) {
        return bookRepository.getById(id);
    }

    @Override
    public List<Book> getBookByAuthor(long idAuthor) {
        Author author = authorRepository.getById(idAuthor);
        return bookRepository.getByAuthor(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    @Override
    public List<Book> getBookByJenre(long idJenre) {
        Jenre jenre = jenreRepository.getById(idJenre);
        return bookRepository.getByJenre(jenre);
    }

    public List<Jenre> getAllJenre() {
        return jenreRepository.getAll();
    }

    public List<Comment> getCommentsByBookId(long idBook) {
        return commentRepository.getByBookId(idBook);
    }

    public List<Comment> getAllComments() {
        return commentRepository.getAll();
    }

    @Transactional
    public Book deleteBook(long id) {
        List<Comment> comments = commentRepository.getByBookId(id);
        comments.forEach(commentRepository::delete);

        Book book = bookRepository.getById(id);
        bookRepository.delete(book);
        return book;
    }

    @Transactional
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

    @Transactional
    public Comment createComment(String message, long idBook) {
        Book book = bookRepository.getById(idBook);
        Comment comment = new Comment()
                .setMessage(message)
                .setBook(book);
        commentRepository.create(comment);
        return comment;
    }
}
