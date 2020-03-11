package ru.otus.spring.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "users")
public class UserObject {

    @Id
    private String id;
    private String name;
    private String password;
    private String role;
}
