package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Jenre;

import java.util.List;

public interface LibraryService {

    List<Book> getAllBooks();

    Book getBook(long id);

    List<Book> getBookByAuthor(long idAuthor);

    List<Author> getAllAuthors();

    List<Book> getBookByJenre(long idJenre);

    List<Jenre> getAllJenre();

    List<Comment> getAllComments();

    List<Comment> getCommentsByBookId(long idBook);

    Book deleteBook(long id);

    Book createBook(String title, long idAuthor, long idJenre);

    Author createAuthor(String name, String surname, String patronymic);

    Jenre createJenre(String type);

    Comment createComment(String message, long idBook);

    Author getAuthor(long id);

    Jenre getJenre(long id);

    Jenre deleteJenre(long id);

    Comment getComment(long id);

    Comment deleteComment(long id);

    Author deleteAuthor(long id);

    void updateAuthor(Author author);

    void updateBook(Book book);

    void updateComment(Comment comment);

    void updateJenre(Jenre jenre);
}
