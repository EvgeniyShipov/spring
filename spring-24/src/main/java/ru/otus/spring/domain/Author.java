package ru.otus.spring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "authors")
@Accessors(chain = true)
public class Author {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "patronymic", nullable = false)
    private String patronymic;

    public String getFullName() {
        return String.format("%s %s %s", surname, name, patronymic);
    }
}
