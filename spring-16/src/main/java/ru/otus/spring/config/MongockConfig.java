package ru.otus.spring.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongockConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.otus.spring.changelog";
    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public Mongock mongock(MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, database, CHANGELOGS_PACKAGE)
                .build();
    }
}
