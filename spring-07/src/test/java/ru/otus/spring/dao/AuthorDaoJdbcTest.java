package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@JdbcTest
@Import(AuthorDaoJdbc.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void getById() {
        int id = 1;
        Author author = authorDaoJdbc.getById(id);

        assertThat(author).isNotNull();
        assertThat(author.getName()).isEqualTo("John");
        assertThat(author.getSurname()).isEqualTo("Steinbeck");
        assertThat(author.getPatronymic()).isEqualTo("Ernst");
    }

    @Test
    void getAll() {
        List<Author> authors = authorDaoJdbc.getAll();

        assertThat(authors).isNotNull();
        assertThat(authors.get(0)).isNotNull();
        assertThat(authors.get(0).getName()).isEqualTo("John");
        assertThat(authors.get(0).getSurname()).isEqualTo("Steinbeck");
        assertThat(authors.get(0).getPatronymic()).isEqualTo("Ernst");
    }

    @Test
    void create() {
        String name = "name";
        String surname = "surname";
        String patronymic = "patronymic";
        Author author = new Author()
                .setName(name)
                .setSurname(surname)
                .setPatronymic(patronymic);

        authorDaoJdbc.create(author);
        List<Author> authors = authorDaoJdbc.getAll();

        assertThat(authors.get(1)).isEqualToIgnoringGivenFields(author, "id");
    }
}