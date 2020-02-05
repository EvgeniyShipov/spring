package ru.otus.spring.service;

import ru.otus.spring.domain.author.Author;
import ru.otus.spring.domain.book.Book;
import ru.otus.spring.domain.comment.Comment;
import ru.otus.spring.domain.jenre.Jenre;

import java.util.List;

public interface LibraryService {

    List<Book> getAllBooks();

    Book getBook(String id);

    List<Book> getBookByAuthor(String idAuthor);

    List<Author> getAllAuthors();

    List<Book> getBookByJenre(String idJenre);

    List<Jenre> getAllJenre();

    List<Comment> getAllComments();

    List<Comment> getCommentsByBookId(String idBook);

    Book deleteBook(String id);

    Book createBook(String title, String idAuthor, String idJenre);

    Author createAuthor(String name, String surname, String patronymic);

    Jenre createJenre(String name);

    Comment createComment(String message, String idBook);

    Author getAuthor(String id);

    Jenre getJenre(String id);

    Jenre deleteJenre(String id);

    Comment getComment(String id);

    Comment deleteComment(String id);

    Author deleteAuthor(String id);

    void updateAuthor(Author author);
}
