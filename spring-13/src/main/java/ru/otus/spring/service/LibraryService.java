package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Jenre;

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
}
