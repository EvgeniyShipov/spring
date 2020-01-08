package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    private static final int BOOK_ID = 1;

    private LibraryService libraryService;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private JenreRepository jenreRepository;
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        authorRepository = mock(AuthorRepository.class);
        jenreRepository = mock(JenreRepository.class);
        commentRepository = mock(CommentRepository.class);
        libraryService = new SimpleLibraryService(bookRepository, authorRepository, jenreRepository, commentRepository);
    }

    @Test
    void getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        when(bookRepository.getAll()).thenReturn(books);

        List<Book> allBooks = libraryService.getAllBooks();

        assertThat(allBooks).isNotNull();
        assertThat(allBooks).isEqualTo(books);

        verify(bookRepository).getAll();
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void getAllComments() {
        Comment comment = new Comment().setMessage("comment");

        when(commentRepository.getAll()).thenReturn(Collections.singletonList(comment));

        List<Comment> resultComments = libraryService.getAllComments();

        assertThat(resultComments).isNotNull();
        assertThat(resultComments).containsOnly(comment);
    }

    @Test
    void getCommentsByBookId() {
        Comment comment = new Comment().setMessage("comment");
        when(commentRepository.getByBookId(BOOK_ID)).thenReturn(Collections.singletonList(comment));

        List<Comment> comments = libraryService.getCommentsByBookId(BOOK_ID);

        assertThat(comments).isNotNull();
        assertThat(comments).containsOnly(comment);
    }

    @Test
    void getBook() {
        Book book = new Book();
        when(bookRepository.getById(BOOK_ID)).thenReturn(book);

        Book resultBook = libraryService.getBook(BOOK_ID);

        assertThat(resultBook).isNotNull();
        assertThat(resultBook).isEqualTo(book);
        verify(bookRepository).getById(BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void getAllAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        when(authorRepository.getAll()).thenReturn(authors);

        List<Author> resultAuthors = libraryService.getAllAuthors();

        assertThat(resultAuthors).isNotNull();
        assertThat(resultAuthors).isEqualTo(authors);
        verify(authorRepository).getAll();
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void getAllJenre() {
        List<Jenre> jenres = Arrays.asList(new Jenre(), new Jenre());
        when(jenreRepository.getAll()).thenReturn(jenres);

        List<Jenre> resultJenre = libraryService.getAllJenre();

        assertThat(resultJenre).isNotNull();
        assertThat(resultJenre).isEqualTo(jenres);
        verify(jenreRepository).getAll();
        verifyNoMoreInteractions(jenreRepository);
    }

    @Test
    void deleteBook() {
        Book book = new Book()
                .setId(BOOK_ID);

        Comment comment = new Comment().setMessage("test message");
        when(commentRepository.getByBookId(BOOK_ID)).thenReturn(Collections.singletonList(comment));
        when(bookRepository.getById(BOOK_ID)).thenReturn(book);

        Book resultBook = libraryService.deleteBook(BOOK_ID);

        assertThat(resultBook).isNotNull();
        assertThat(resultBook.getId()).isEqualTo(BOOK_ID);

        verify(commentRepository).getByBookId(BOOK_ID);
        verify(commentRepository).delete(comment);
        verify(bookRepository).getById(BOOK_ID);
        verify(bookRepository).delete(book);

        verifyNoMoreInteractions(commentRepository);
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void createBook() {
        String title = "Title";
        Author author = new Author().setId(1);
        Jenre jenre = new Jenre().setId(1).setType("tragedy");

        when(authorRepository.getById(author.getId())).thenReturn(author);
        when(jenreRepository.getById(jenre.getId())).thenReturn(jenre);

        Book resultBook = libraryService.createBook(title, author.getId(), jenre.getId());

        assertThat(resultBook).isNotNull();
        assertThat(resultBook.getTitle()).isEqualTo(title);
        assertThat(resultBook.getAuthor().getId()).isEqualTo(author.getId());
        assertThat(resultBook.getJenre()).isEqualTo(jenre);
        verify(bookRepository).create(any(Book.class));
        verify(authorRepository).getById(author.getId());
        verify(jenreRepository).getById(jenre.getId());
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(authorRepository);
        verifyNoMoreInteractions(jenreRepository);
    }

    @Test
    void createAuthor() {
        String name = "name";
        String surname = "surname";
        String patronymic = "patronymic";

        Author author = new Author()
                .setName(name)
                .setSurname(surname)
                .setPatronymic(patronymic);

        when(authorRepository.create(any(Author.class))).thenReturn(author);

        Author result = libraryService.createAuthor(name, surname, patronymic);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getSurname()).isEqualTo(surname);
        assertThat(result.getPatronymic()).isEqualTo(patronymic);
        verify(authorRepository).create(any(Author.class));
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void createJenre() {
        String horror = "horror";
        Jenre jenre = new Jenre().setType(horror);
        when(jenreRepository.create(any(Jenre.class))).thenReturn(jenre);

        Jenre result = libraryService.createJenre(horror);

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo(jenre.getType());
    }

    @Test
    void createComment() {
        Book book = new Book()
                .setId(BOOK_ID);

        when(bookRepository.getById(BOOK_ID)).thenReturn(book);

        String message = "test message";

        Comment result = libraryService.createComment(message, BOOK_ID);

        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(message);
    }
}