package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import ru.otus.spring.service.*;

import java.util.Locale;
import java.util.Scanner;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${questions.ru.path}")
    private Resource resourceRu;
    @Value("${questions.en.path}")
    private Resource resourceEn;

    @Bean
    public InOutService inOutService(MessageSource messageSource, @Value("${language}") Locale language) {
        return new ConsoleInOutService(new Scanner(System.in), System.out, messageSource, language);
    }

    @Bean
    public QuestionService questionService(@Value("${language}") String language) {
        QuestionService questionService = new SimpleQuestionService();
        Resource resource = "ru".equals(language)
                ? resourceRu
                : resourceEn;

        questionService.init(resource);
        return questionService;
    }

    @Bean
    public GameController gameController(QuestionService gameService, InOutService inOutService) {
        return new GameController(gameService, inOutService);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
