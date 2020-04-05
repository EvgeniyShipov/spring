package ru.otus.spring.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import static org.springframework.boot.actuate.health.Health.up;

@Component
public class AppHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {

        return up().build();
    }
}
