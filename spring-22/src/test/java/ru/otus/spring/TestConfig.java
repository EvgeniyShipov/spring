package ru.otus.spring;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.otus.spring.repository.*;

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
    private UserDetailsService libraryUserDetailsService;
    @MockBean
    private UserRepository userRepository;
}
