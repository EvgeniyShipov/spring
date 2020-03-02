package ru.otus.spring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.handler.BookHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BookRouter {

    @Bean
    public RouterFunction<ServerResponse> bookRoute(BookHandler handler) {
        return route()
                .GET("books", handler::getAllBooks)
                .GET("books/{id}", handler::getBook)
                .POST("books", handler::createBook)
                .PUT("books/{id}", handler::updateBook)
                .DELETE("books/{id}", handler::deleteBook)
                .build();
    }
}
