package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.domain.Bug;
import ru.otus.spring.service.DevOpsEngineer;
import ru.otus.spring.service.JuniorProgrammer;
import ru.otus.spring.service.SeniorProgrammer;

import java.util.Arrays;

import static org.springframework.integration.scheduling.PollerMetadata.DEFAULT_POLLER;


@IntegrationComponentScan
@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public QueueChannel programChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel releaseChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(100).maxMessagesPerPoll(5).get();
    }

    @Bean
    public IntegrationFlow programFlow() {
        return IntegrationFlows.from("programChannel")
                .split()
                .route((Bug bug) -> Arrays.asList("blocker", "critical").contains(bug.getName())
                        ? "seniorProgrammerChannel"
                        : "juniorProgrammerChannel")
                .get();
    }

    @Bean
    public IntegrationFlow juniorProgrammerFlow(JuniorProgrammer programmer) {
        return IntegrationFlows.from("juniorProgrammerChannel")
                .handle(programmer, "fixBug")
                .channel("devOpsEngineerChannel")
                .get();
    }

    @Bean
    public IntegrationFlow seniorProgrammerFlow(SeniorProgrammer programmer) {
        return IntegrationFlows.from("seniorProgrammerChannel")
                .handle(programmer, "fixBug")
                .channel("devOpsEngineerChannel")
                .get();
    }

    @Bean
    public IntegrationFlow devOpsEngineerFlow(DevOpsEngineer engineer) {
        return IntegrationFlows.from("devOpsEngineerChannel")
                .handle(engineer, "installFix")
                .aggregate()
                .convert(String.class)
                .channel("releaseChannel")
                .get();
    }
}
