package ru.otus.spring.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

import static org.springframework.boot.actuate.health.Health.up;

@Component
public class AppHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if (new Random().nextBoolean()) {
            return up().withDetail("message", "I'm ok!").build();
        } else {
            return up().withDetail("message", "Check again").build();
        }
    }
}
