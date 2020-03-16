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
        return bookRepository.findAll();
    }

    public Book getBook(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Книга не найдена"));
    }

    @Override
    public List<Book> getBookByAuthor(long idAuthor) {
        Author author = getAuthor(idAuthor);
        return bookRepository.findByAuthor(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Book> getBookByJenre(long idJenre) {
        Jenre jenre = getJenre(idJenre);
        return bookRepository.findByJenre(jenre);
    }

    public List<Jenre> getAllJenre() {
        return jenreRepository.findAll();
    }

    public List<Comment> getCommentsByBookId(long idBook) {
        return commentRepository.findByBookId(idBook);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Transactional
    public Book deleteBook(long id) {
        commentRepository.deleteAllByBookId(id);
        Book book = getBook(id);
        bookRepository.delete(book);
        return book;
    }

    @Transactional
    public Book createBook(String title, long idAuthor, long idJenre) {
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

    public Jenre createJenre(String type) {
        Jenre jenre = new Jenre().setType(type);
        return jenreRepository.save(jenre);
    }

    @Transactional
    public Comment createComment(String message, long idBook) {
        Book book = getBook(idBook);
        Comment comment = new Comment()
                .setMessage(message)
                .setBook(book);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Author getAuthor(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Автор не найден"));
    }

    @Override
    public Jenre getJenre(long id) {
        return jenreRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Жанр не найден"));
    }

    @Override
    public Jenre deleteJenre(long id) {
        if (bookRepository.existsById(Long.valueOf(id))) {
            Jenre jenre = getJenre(id);
            jenreRepository.delete(jenre);
            return jenre;
        }
        throw new IllegalStateException("Нельзя удалить жанр, оставив книги");
    }

    @Override
    public Comment getComment(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Жанр не найден"));
    }

    @Override
    public Comment deleteComment(long id) {
        Comment comment = getComment(id);
        commentRepository.delete(comment);
        return comment;
    }

    @Override
    public Author deleteAuthor(long id) {
        if (bookRepository.existsById(Long.valueOf(id))) {
            Author author = getAuthor(id);
            authorRepository.delete(author);
            return author;
        }
        throw new IllegalStateException("Нельзя удалить автора, оставив его книги");
    }

    @Override
    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void updateJenre(Jenre jenre) {
        jenreRepository.save(jenre);
    }
}
