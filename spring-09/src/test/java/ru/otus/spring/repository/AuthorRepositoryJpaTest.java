package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {
    private static final int AUTHOR_ID = 1;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getById() {
        Author actualAuthor = authorRepository.getById(AUTHOR_ID);
        Author expectedAuthor = entityManager.find(Author.class, actualAuthor.getId());

        assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }

    @Test
    void getAll() {
        List<Author> actualAuthors = authorRepository.getAll();
        Author expectedAuthor = entityManager.find(Author.class, actualAuthors.get(0).getId());

        assertThat(actualAuthors).isNotNull();
        assertThat(expectedAuthor).isEqualTo(actualAuthors.get(0));
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

        Author actualAuthor = authorRepository.create(author);
        Author expectedAuthor = entityManager.find(Author.class, actualAuthor.getId());

        assertThat(expectedAuthor).isEqualTo(actualAuthor);
    }
}