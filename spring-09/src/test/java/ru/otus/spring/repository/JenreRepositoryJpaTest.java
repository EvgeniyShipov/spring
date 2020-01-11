package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Jenre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JenreRepositoryJpa.class)
class JenreRepositoryJpaTest {
    private static final int JENRE_ID = 1;

    @Autowired
    private JenreRepository jenreRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getAll() {
        List<Jenre> actualJenres = jenreRepository.getAll();

        Jenre expectedJenre = entityManager.find(Jenre.class, actualJenres.get(0).getId());
        assertThat(expectedJenre).isEqualTo(actualJenres.get(0));

        expectedJenre = entityManager.find(Jenre.class, actualJenres.get(1).getId());
        assertThat(expectedJenre).isEqualTo(actualJenres.get(1));

        expectedJenre = entityManager.find(Jenre.class, actualJenres.get(2).getId());
        assertThat(expectedJenre).isEqualTo(actualJenres.get(2));

        expectedJenre = entityManager.find(Jenre.class, actualJenres.get(3).getId());
        assertThat(expectedJenre).isEqualTo(actualJenres.get(3));
    }

    @Test
    void getById() {
        Jenre actualJenre = jenreRepository.getById(JENRE_ID);
        Jenre expectedJenre = entityManager.find(Jenre.class, actualJenre.getId());

        assertThat(expectedJenre).isEqualTo(actualJenre);
    }

    @Test
    void create() {
        String type = "HUMOR";
        Jenre jenre = new Jenre()
                .setType(type);

        Jenre actualJenre = jenreRepository.create(jenre);
        Jenre expectedJenre = entityManager.find(Jenre.class, actualJenre.getId());

        assertThat(expectedJenre).isEqualTo(actualJenre);
    }
}