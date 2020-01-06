package ru.otus.spring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "comments")
@Accessors(chain = true)
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "message", nullable = false)
    private String message;
}
