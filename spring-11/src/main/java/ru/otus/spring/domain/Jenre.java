package ru.otus.spring.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
    private long id;
    @Column(name = "type", nullable = false, unique = true)
    private String type;
}
