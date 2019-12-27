package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.JenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final JenreDao jenreDao;

    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    public Book getBook(long id) {
        return bookDao.getById(id);
    }

    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    public List<Jenre> getAllJenre() {
        return jenreDao.getAll();
    }

    public Book deleteBook(long id) {
        Book book = bookDao.getById(id);
        bookDao.deleteById(id);
        return book;
    }

    public Book createBook(String title, long idAuthor, long idJenre) {
        Book book = new Book()
                .setTitle(title)
                .setAuthor(authorDao.getById(idAuthor))
                .setJenre(jenreDao.getById(idJenre));
        bookDao.create(book);
        return book;
    }

    public Author createAuthor(String name, String surname, String patronymic) {
        Author author = new Author()
                .setName(name)
                .setSurname(surname)
                .setPatronymic(patronymic);
        authorDao.create(author);
        return author;
    }

    public Jenre createJenre(String name) {
        Jenre jenre = new Jenre().setType(name);
        jenreDao.create(jenre);
        return jenre;
    }
}
