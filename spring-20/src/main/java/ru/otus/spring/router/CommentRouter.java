package ru.otus.spring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.handler.CommentHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CommentRouter {

    @Bean
    public RouterFunction<ServerResponse> commentRoute(CommentHandler handler) {
        return route()
                .GET("comments", handler::getAllComments)
                .GET("comments/{id}", handler::getComment)
                .POST("comments", handler::createComment)
                .PUT("comments/{id}", handler::updateComment)
                .DELETE("comments/{id}", handler::deleteComment)
                .build();
    }
}
