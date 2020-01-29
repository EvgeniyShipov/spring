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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    private static final String BOOK_ID = "1";
    private static final String AUTHOR_ID = "1";
    private static final String JENRE_ID = "1";

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
        List<Book> books = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> allBooks = libraryService.getAllBooks();

        assertThat(allBooks).isNotNull();
        assertThat(allBooks).isEqualTo(books);

        verify(bookRepository).findAll();
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void getAllComments() {
        Comment comment = new Comment().setMessage("comment");

        when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));

        List<Comment> resultComments = libraryService.getAllComments();

        assertThat(resultComments).isNotNull();
        assertThat(resultComments).containsOnly(comment);
    }

    @Test
    void getCommentsByBookId() {
        Comment comment = new Comment().setMessage("comment");
        when(commentRepository.findByBookId(BOOK_ID)).thenReturn(Collections.singletonList(comment));

        List<Comment> comments = libraryService.getCommentsByBookId(BOOK_ID);

        assertThat(comments).isNotNull();
        assertThat(comments).containsOnly(comment);
    }

    @Test
    void getBook() {
        Book book = new Book();
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));

        Book resultBook = libraryService.getBook(BOOK_ID);

        assertThat(resultBook).isNotNull();
        assertThat(resultBook).isEqualTo(book);
        verify(bookRepository).findById(BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> resultAuthors = libraryService.getAllAuthors();

        assertThat(resultAuthors).isNotNull();
        assertThat(resultAuthors).isEqualTo(authors);
        verify(authorRepository).findAll();
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void getAllJenre() {
        List<Jenre> jenres = Arrays.asList(new Jenre(), new Jenre());
        when(jenreRepository.findAll()).thenReturn(jenres);

        List<Jenre> resultJenre = libraryService.getAllJenre();

        assertThat(resultJenre).isNotNull();
        assertThat(resultJenre).isEqualTo(jenres);
        verify(jenreRepository).findAll();
        verifyNoMoreInteractions(jenreRepository);
    }

    @Test
    void getBookByAuthor() {
        Author author = new Author().setName("name").setSurname("surname");
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(author));

        List<Book> books = new ArrayList<>();
        when(bookRepository.findByAuthor(author)).thenReturn(books);

        List<Book> resultBooks = libraryService.getBookByAuthor(AUTHOR_ID);

        assertThat(resultBooks).isEqualTo(books);
    }

    @Test
    void getBookByJenre() {
        Jenre jenre = new Jenre().setType("jenre");
        when(jenreRepository.findById(JENRE_ID)).thenReturn(Optional.of(jenre));

        List<Book> books = new ArrayList<>();
        when(bookRepository.findByJenre(jenre)).thenReturn(books);

        List<Book> resultBooks = libraryService.getBookByJenre(JENRE_ID);

        assertThat(resultBooks).isEqualTo(books);
    }

    @Test
    void deleteBook() {
        Book book = new Book()
                .setId(BOOK_ID);

        Comment comment = new Comment().setMessage("test message");
        when(commentRepository.findByBookId(BOOK_ID)).thenReturn(Collections.singletonList(comment));
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));

        Book resultBook = libraryService.deleteBook(BOOK_ID);

        assertThat(resultBook).isNotNull();
        assertThat(resultBook.getId()).isEqualTo((BOOK_ID));

        verify(commentRepository).deleteAllByBookId(BOOK_ID);
        verify(bookRepository).findById(BOOK_ID);
        verify(bookRepository).delete(book);

        verifyNoMoreInteractions(commentRepository);
        verifyNoMoreInteractions(bookRepository);
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void createBook() {
        String title = "Title";
        Author author = new Author().setId(AUTHOR_ID);
        Jenre jenre = new Jenre().setId(JENRE_ID).setType("tragedy");

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(jenreRepository.findById(jenre.getId())).thenReturn(Optional.of(jenre));

        Book resultBook = libraryService.createBook(title, author.getId(), jenre.getId());

        assertThat(resultBook).isNotNull();
        assertThat(resultBook.getTitle()).isEqualTo(title);
        assertThat(resultBook.getAuthor().getId()).isEqualTo(author.getId());
        assertThat(resultBook.getJenre()).isEqualTo(jenre);
        verify(bookRepository).save(any(Book.class));
        verify(authorRepository).findById(author.getId());
        verify(jenreRepository).findById(jenre.getId());
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

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = libraryService.createAuthor(name, surname, patronymic);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getSurname()).isEqualTo(surname);
        assertThat(result.getPatronymic()).isEqualTo(patronymic);
        verify(authorRepository).save(any(Author.class));
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void createJenre() {
        String horror = "horror";
        Jenre jenre = new Jenre().setType(horror);
        when(jenreRepository.save(any(Jenre.class))).thenReturn(jenre);

        Jenre result = libraryService.createJenre(horror);

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo(jenre.getType());
    }

    @Test
    void createComment() {
        Book book = new Book()
                .setId(BOOK_ID);

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));

        String message = "test message";

        Comment result = libraryService.createComment(message, BOOK_ID);

        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo(message);
    }
}