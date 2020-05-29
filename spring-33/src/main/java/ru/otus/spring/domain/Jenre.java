package ru.otus.spring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "jenre")
@Accessors(chain = true)
public class Jenre {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "type", nullable = false, unique = true)
    private String type;
}
