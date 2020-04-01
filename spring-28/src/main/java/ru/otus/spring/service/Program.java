package ru.otus.spring.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Bug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class Program {

    private static final String[] BUGS = {"blocker", "critical", "major", "minor", "info"};

    public Collection<Bug> getBugs() {
        List<Bug> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 10); ++i) {
            items.add(getBug());
        }
        return items;
    }

    private Bug getBug() {
        return new Bug(BUGS[RandomUtils.nextInt(0, BUGS.length)], RandomUtils.nextInt(100, 1000));
    }
}
