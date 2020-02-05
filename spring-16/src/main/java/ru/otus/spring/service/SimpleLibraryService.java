package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.author.Author;
import ru.otus.spring.domain.author.AuthorRepository;
import ru.otus.spring.domain.book.Book;
import ru.otus.spring.domain.book.BookRepository;
import ru.otus.spring.domain.comment.Comment;
import ru.otus.spring.domain.comment.CommentRepository;
import ru.otus.spring.domain.jenre.Jenre;
import ru.otus.spring.domain.jenre.JenreRepository;

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
        Author author = getAuthor(idAuthor);
        return bookRepository.findByAuthor(author);
    }

    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public List<Book> getBookByJenre(String idJenre) {
        Jenre jenre = getJenre(idJenre);
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
        Book book = getBook(id);
        bookRepository.delete(book);
        return book;
    }

    @Transactional
    public Book createBook(String title, String idAuthor, String idJenre) {
        Book book = new Book()
                .setTitle(title)
                .setAuthor(getAuthor(idAuthor))
                .setJenre(getJenre(idJenre));
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
        Book book = getBook(idBook);
        Comment comment = new Comment()
                .setMessage(message)
                .setBook(book);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Author getAuthor(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Автор не найден"));
    }

    @Override
    public Jenre getJenre(String id) {
        return jenreRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Жанр не найден"));
    }

    @Override
    public Jenre deleteJenre(String id) {
        Jenre jenre = getJenre(id);
        jenreRepository.delete(jenre);
        return jenre;
    }

    @Override
    public Comment getComment(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Жанр не найден"));
    }

    @Override
    public Comment deleteComment(String id) {
        Comment comment = getComment(id);
        commentRepository.delete(comment);
        return comment;
    }

    @Override
    public Author deleteAuthor(String id) {
        Author author = getAuthor(id);
        authorRepository.delete(author);
        return author;
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }
}
