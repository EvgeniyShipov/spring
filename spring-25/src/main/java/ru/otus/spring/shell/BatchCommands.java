package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import static ru.otus.spring.config.JobConfig.DB_MIGRATION_JOB;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job importUserJob;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final JenreRepository jenreRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    //http://localhost:8080/h2-console/

    @SneakyThrows
    @ShellMethod(value = "startMigrationJob", key = "sm")
    public void startMigrationJobWithJobLauncher() {
        JobExecution execution = jobLauncher.run(importUserJob, new JobParameters());
        System.out.println(execution);
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(DB_MIGRATION_JOB));
    }

    @ShellMethod(value = "showDB", key = "sdb")
    public void showDB() {
        jenreRepository.findAll().forEach(System.out::println);
        authorRepository.findAll().forEach(System.out::println);
        bookRepository.findAll().forEach(System.out::println);
        commentRepository.findAll().forEach(System.out::println);
    }
}
