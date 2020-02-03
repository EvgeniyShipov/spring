package ru.otus.spring.domain.jenre;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "jenre")
public class Jenre {

    @Id
    private String id;
    private String type;
}
