package ru.otus.spring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "books")
@Accessors(chain = true)
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "id_author")
    private Author author;

    @OneToOne
    @JoinColumn(name = "id_jenre")
    private Jenre jenre;
}
