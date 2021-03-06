package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

public interface LibraryService {

    List<Book> getAllBooks();

    Book getBook(long id);

    List<Author> getAllAuthors();

    List<Jenre> getAllJenre();

    Book deleteBook(long id);

    Book createBook(String title, long idAuthor, long idJenre);

    Author createAuthor(String name, String surname, String patronymic);

    Jenre createJenre(String name);
}
