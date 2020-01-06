package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Jenre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JenreRepositoryJpa.class)
class JenreRepositoryJpaTest {

    @Autowired
    private JenreRepository jenreRepository;

    @Test
    void getAll() {
        List<Jenre> jenres = jenreRepository.getAll();

        assertThat(jenres).isNotNull();
        assertThat(jenres.get(0).getType()).isEqualTo("COMEDY");
        assertThat(jenres.get(1).getType()).isEqualTo("TRAGEDY");
        assertThat(jenres.get(2).getType()).isEqualTo("DRAMA");
        assertThat(jenres.get(3).getType()).isEqualTo("HORROR");
    }

    @Test
    void getById() {
        int id = 1;
        Jenre jenre = jenreRepository.getById(id);

        assertThat(jenre).isNotNull();
        assertThat(jenre.getType()).isEqualTo("COMEDY");
    }

    @Test
    void create() {
        String type = "HUMOR";
        Jenre jenre = new Jenre()
                .setType(type);

        Jenre result = jenreRepository.create(jenre);

        assertThat(result.getType()).isEqualTo(type);
    }
}