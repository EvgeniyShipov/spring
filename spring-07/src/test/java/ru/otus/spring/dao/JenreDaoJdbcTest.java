package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Jenre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JenreDaoJdbc.class)
class JenreDaoJdbcTest {

    @Autowired
    private JenreDaoJdbc jenreDaoJdbc;

    @Test
    void getAll() {
        List<Jenre> jenres = jenreDaoJdbc.getAll();

        assertThat(jenres).isNotNull();
        assertThat(jenres.get(0).getType()).isEqualTo("COMEDY");
        assertThat(jenres.get(1).getType()).isEqualTo("TRAGEDY");
        assertThat(jenres.get(2).getType()).isEqualTo("DRAMA");
        assertThat(jenres.get(3).getType()).isEqualTo("HORROR");
    }
}