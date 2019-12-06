package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.service.ConsoleInOutService;
import ru.otus.spring.service.InOutService;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.SimpleQuestionService;

import java.util.Locale;
import java.util.Scanner;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public InOutService consoleInOutService(MessageSource messageSource, @Value("${language}") Locale language) {
        return new ConsoleInOutService(new Scanner(System.in), System.out, messageSource, language);
    }

    @Bean
    public QuestionService simpleQuestionService(@Value("${language}") String language,
                                                 @Value("${questions.file.name}") String fileName) {
        QuestionService questionService = new SimpleQuestionService();
        Resource resource = new ClassPathResource(fileName.replace(".", "_" + language + "."));

        questionService.init(resource);
        return questionService;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
