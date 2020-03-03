package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.UserObject;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserObject, Long> {

    Optional<UserObject> findByName(String name);
}
