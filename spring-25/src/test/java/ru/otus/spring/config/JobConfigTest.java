package ru.otus.spring.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.JenreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.config.JobConfig.DB_MIGRATION_JOB;

@SpringBootTest
@SpringBatchTest
class JobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private JenreRepository jenreRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(DB_MIGRATION_JOB);

        assertThat(jenreRepository.findAll()).isEmpty();
        assertThat(authorRepository.findAll()).isEmpty();
        assertThat(bookRepository.findAll()).isEmpty();
        assertThat(commentRepository.findAll()).isEmpty();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParameters());

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        assertThat(jenreRepository.findAll()).isNotEmpty();
        assertThat(authorRepository.findAll()).isNotEmpty();
        assertThat(bookRepository.findAll()).isNotEmpty();
        assertThat(commentRepository.findAll()).isNotEmpty();
    }
}