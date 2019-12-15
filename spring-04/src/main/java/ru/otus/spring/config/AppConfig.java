package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.service.*;

import java.util.Locale;
import java.util.Scanner;

@Configuration
public class AppConfig {

    @Value("${questions.file.name}")
    private String fileName;

    @Value("${application.language}")
    private Locale language;

    @Bean
    public MessageService localiseMessageService(MessageSource messageSource, InOutService InOutService) {
        return new LocaliseMessageService(language, messageSource, InOutService);
    }

    @Bean
    public InOutService consoleInOutService() {
        return new ConsoleInOutService(new Scanner(System.in), System.out);
    }

    @Bean
    public QuestionService simpleQuestionService() {
        QuestionService questionService = new SimpleQuestionService();
        Resource resource = new ClassPathResource(fileName.replace(".", "_" + language + "."));

        questionService.init(resource);
        return questionService;
    }
}
