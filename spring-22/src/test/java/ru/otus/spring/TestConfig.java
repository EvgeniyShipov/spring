package ru.otus.spring;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.repository.*;
import ru.otus.spring.service.LibraryUserDetailsService;

@TestConfiguration
public class TestConfig {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private JenreRepository jenreRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private LibraryUserDetailsService libraryUserDetailsService;
    @MockBean
    private UserRepository userRepository;
}
