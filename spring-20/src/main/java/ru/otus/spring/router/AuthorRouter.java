package ru.otus.spring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.handler.AuthorHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthorRouter {

    @Bean
    public RouterFunction<ServerResponse> authorRoute(AuthorHandler handler) {
        return route()
                .GET("authors", handler::getAllAuthors)
                .GET("authors/{id}", handler::getAuthor)
                .POST("authors", handler::createAuthor)
                .PUT("authors/{id}", handler::updateAuthor)
                .DELETE("authors/{id}", handler::deleteAuthor)
                .build();
    }
}
