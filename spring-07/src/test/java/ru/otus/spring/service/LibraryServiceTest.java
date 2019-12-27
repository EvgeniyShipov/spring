package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        List<Jenre> jenres = Arrays.asList(new Jenre(), new Jenre());
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
        Book book = new Book()
                .setId(bookId);

        when(bookDao.getById(bookId)).thenReturn(book);

        Book resultBook = libraryService.deleteBook(bookId);

        assertThat(resultBook).isNotNull();
        assertThat(resultBook.getId()).isEqualTo(bookId);

        verify(bookDao).getById(bookId);
        verify(bookDao).deleteById(bookId);

        verifyNoMoreInteractions(bookDao);
        verifyNoMoreInteractions(authorDao);
    }

    @Test
    void createBook() {
        String title = "Title";
        Author author =  new Author().setId(1);
        Jenre jenre = new Jenre().setId(1).setType("tragedy");

        when(authorDao.getById(author.getId())).thenReturn(author);
        when(jenreDao.getById(jenre.getId())).thenReturn(jenre);

        Book resultBook = libraryService.createBook(title, author.getId(), jenre.getId());

        assertThat(resultBook).isNotNull();
        assertThat(resultBook.getTitle()).isEqualTo(title);
        assertThat(resultBook.getAuthor().getId()).isEqualTo(author.getId());
        assertThat(resultBook.getJenre()).isEqualTo(jenre);
        verify(bookDao).create(any(Book.class));
        verify(authorDao).getById(author.getId());
        verify(jenreDao).getById(jenre.getId());
        verifyNoMoreInteractions(bookDao);
        verifyNoMoreInteractions(authorDao);
        verifyNoMoreInteractions(jenreDao);
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

    @Test
    void createJenre() {

        String horror = "horror";
        Jenre jenre = libraryService.createJenre(horror);

        assertThat(jenre).isNotNull();
        assertThat(jenre.getType()).isEqualTo(horror);
    }
}