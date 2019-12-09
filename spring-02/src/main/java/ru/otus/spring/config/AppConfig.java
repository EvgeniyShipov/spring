package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.service.*;

import java.util.Locale;
import java.util.Scanner;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public MessageService localiseMessageService(@Value("${language}") Locale language, MessageSource messageSource,
                                                  InOutService InOutService) {
        return new LocaliseMessageService(language, messageSource, InOutService);
    }

    @Bean
    public InOutService consoleInOutService() {
        return new ConsoleInOutService(new Scanner(System.in), System.out);
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
