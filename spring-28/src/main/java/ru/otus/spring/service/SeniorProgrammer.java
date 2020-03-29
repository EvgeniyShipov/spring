package ru.otus.spring.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Bug;
import ru.otus.spring.domain.Fix;

@Service
public class SeniorProgrammer {

    public Fix fixBug(Bug bug) throws InterruptedException {
        System.out.println("Hard working on " + bug.getName() + " bug");
        Thread.sleep(bug.getTimeToFix());
        System.out.println("Fixed " + bug.getName() + " bug");
        return new Fix(bug.getName(), RandomUtils.nextInt(100, 1000));
    }
}
