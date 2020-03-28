package ru.otus.spring.config;

import lombok.extern.java.Log;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Jenre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import java.util.HashMap;

import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Log
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    public static final String DB_MIGRATION_JOB = "dbMigrationJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importUserJob(Step setup, Step step1, Step step2, Step step3, Step step4) {
        return jobBuilderFactory.get(DB_MIGRATION_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(setup)
                .next(step1)
                .next(step2)
                .next(step3)
                .next(step4)
                .end()
                .build();
    }

    @Bean
    public Step setup(BookRepository bookRepository, AuthorRepository authorRepository,
                      CommentRepository commentRepository, JenreRepository jenreRepository) {
        return stepBuilderFactory.get("clean-table").tasklet((contribution, chunkContext) -> {
            jenreRepository.deleteAll();
            authorRepository.deleteAll();
            bookRepository.deleteAll();
            commentRepository.deleteAll();
            return FINISHED;
        }).build();
    }

    @Bean
    public Step step1(ItemReader<Jenre> jenreReader, ItemWriter<Jenre> jenreWriter) {
        return stepBuilderFactory.get("step1")
                .<Jenre, Jenre>chunk(CHUNK_SIZE)
                .reader(jenreReader)
                .writer(jenreWriter)
                .build();
    }

    @Bean
    public Step step2(ItemReader<Author> authorReader, ItemWriter<Author> authorWriter) {
        return stepBuilderFactory.get("step2")
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(authorReader)
                .writer(authorWriter)
                .build();
    }

    @Bean
    public Step step3(ItemReader<Book> bookReader, ItemWriter<Book> bookWriter) {
        return stepBuilderFactory.get("step3")
                .<Book, Book>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public Step step4(ItemReader<Comment> commentReader, ItemWriter<Comment> commentWriter) {
        return stepBuilderFactory.get("step4")
                .<Comment, Comment>chunk(CHUNK_SIZE)
                .reader(commentReader)
                .writer(commentWriter)
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Jenre> jenreReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Jenre>()
                .name("jenreItemReader")
                .template(template)
                .targetType(Jenre.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Author> authorReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Author>()
                .name("authorItemReader")
                .template(template)
                .targetType(Author.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Book> bookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Book>()
                .name("bookItemReader")
                .template(template)
                .targetType(Book.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Comment> commentReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Comment>()
                .name("commentItemReader")
                .template(template)
                .targetType(Comment.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<Jenre> jenreWriter(JenreRepository jenreRepository) {
        return new RepositoryItemWriterBuilder<Jenre>()
                .repository(jenreRepository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<Author> authorWriter(AuthorRepository authorRepository) {
        return new RepositoryItemWriterBuilder<Author>()
                .repository(authorRepository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<Book> bookWriter(BookRepository bookRepository) {
        return new RepositoryItemWriterBuilder<Book>()
                .repository(bookRepository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<Comment> commentWriter(CommentRepository commentRepository) {
        return new RepositoryItemWriterBuilder<Comment>()
                .repository(commentRepository)
                .methodName("save")
                .build();
    }
}
