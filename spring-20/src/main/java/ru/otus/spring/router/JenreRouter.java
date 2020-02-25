package ru.otus.spring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.handler.JenreHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class JenreRouter {

    @Bean
    public RouterFunction<ServerResponse> jenreRoute(JenreHandler handler) {
        return route()
                .GET("jenres", handler::getAllJenre)
                .GET("jenres/{id}", handler::getJenre)
                .POST("jenres", handler::createJenre)
                .PUT("jenres/{id}", handler::updateJenre)
                .DELETE("jenres/{id}", handler::deleteJenre)
                .build();
    }
}
