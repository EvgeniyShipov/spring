package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.JenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Jenre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static ru.otus.spring.domain.Jenre.TRAGEDY;

@JdbcTest
class LibraryServiceTest {
    private LibraryService libraryService;
    private BookDao bookDao;
    private AuthorDao authorDao;
    private JenreDao jenreDao;

    @BeforeEach
    void setUp() {
        bookDao = mock(BookDao.class);
        authorDao = mock(AuthorDao.class);
        jenreDao = mock(JenreDao.class);
        libraryService = new LibraryService(bookDao, authorDao, jenreDao);
    }

    @Test
    void getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        when(bookDao.getAll()).thenReturn(books);

        List<Book> allBooks = libraryService.getAllBooks();

        assertThat(allBooks).isNotNull();
        assertThat(allBooks).isEqualTo(books);
        verify(bookDao).getAll();
        verifyNoMoreInteractions(bookDao);
    }

    @Test
    void getBook() {
        int bookId = 1;
        Book book = new Book();
        when(bookDao.getById(bookId)).thenReturn(book);

        Book resultBook = libraryService.getBook(bookId);

        assertThat(resultBook).isNotNull();
        assertThat(resultBook).isEqualTo(book);
        verify(bookDao).getById(bookId);
        verifyNoMoreInteractions(bookDao);
    }

    @Test
    void getAllAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        when(authorDao.getAll()).thenReturn(authors);

        List<Author> resultAuthors = libraryService.getAllAuthors();

        assertThat(resultAuthors).isNotNull();
        assertThat(resultAuthors).isEqualTo(authors);
        verify(authorDao).getAll();
        verifyNoMoreInteractions(authorDao);
    }

    @Test
    void getAllJenre() {
        List<Jenre> jenres = Arrays.asList(Jenre.values());
        when(jenreDao.getAll()).thenReturn(jenres);

        List<Jenre> resultJenre = libraryService.getAllJenre();

        assertThat(resultJenre).isNotNull();
        assertThat(resultJenre).isEqualTo(jenres);
        verify(jenreDao).getAll();
        verifyNoMoreInteractions(jenreDao);
    }

    @Test
    void deleteBook() {
        int bookId = 1;
        int authorId = 1;
        Book book = new Book()
                .setId(bookId)
                .setIdAuthor(authorId);

        when(bookDao.getById(bookId)).thenReturn(book);
        when(bookDao.getByAuthor(authorId)).thenReturn(Collections.emptyList());

        Book resultBook = libraryService.deleteBook(bookId);

        assertThat(resultBook).isNotNull();
        assertThat(resultBook.getId()).isEqualTo(bookId);
        assertThat(resultBook.getIdAuthor()).isEqualTo(authorId);

        verify(bookDao).getById(bookId);
        verify(bookDao).deleteById(bookId);
        verify(bookDao).getByAuthor(authorId);
        verify(authorDao).deleteById(authorId);

        verifyNoMoreInteractions(bookDao);
        verifyNoMoreInteractions(authorDao);
    }

    @Test
    void createBook() {
        String title = "Title";
        int authorId = 1;
        Jenre jenre = TRAGEDY;

        Book resultBook = libraryService.createBook(title, authorId, jenre);

        assertThat(resultBook).isNotNull();
        assertThat(resultBook.getTitle()).isEqualTo(title);
        assertThat(resultBook.getIdAuthor()).isEqualTo(authorId);
        assertThat(resultBook.getIdJenre()).isEqualTo(jenre.ordinal());
        verify(bookDao).create(any(Book.class));
        verifyNoMoreInteractions(bookDao);
    }

    @Test
    void createAuthor() {
        String name = "name";
        String surname = "surname";
        String patronymic = "patronymic";

        Author resultAuthor = libraryService.createAuthor(name, surname, patronymic);

        assertThat(resultAuthor).isNotNull();
        assertThat(resultAuthor.getName()).isEqualTo(name);
        assertThat(resultAuthor.getSurname()).isEqualTo(surname);
        assertThat(resultAuthor.getPatronymic()).isEqualTo(patronymic);
        verify(authorDao).create(any(Author.class));
        verifyNoMoreInteractions(authorDao);
    }
}