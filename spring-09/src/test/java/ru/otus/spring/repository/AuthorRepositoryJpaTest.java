package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void getById() {
        int id = 1;
        Author author = authorRepository.getById(id);

        assertThat(author).isNotNull();
        assertThat(author.getName()).isEqualTo("John");
        assertThat(author.getSurname()).isEqualTo("Steinbeck");
        assertThat(author.getPatronymic()).isEqualTo("Ernst");
    }

    @Test
    void getAll() {
        List<Author> authors = authorRepository.getAll();

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

        Author result = authorRepository.create(author);

        assertThat(result).isEqualToIgnoringGivenFields(author, "id");
    }
}