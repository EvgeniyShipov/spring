package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Fix;

@Service
public class DevOpsEngineer {

    public String installFix(Fix fix) throws InterruptedException {
        System.out.println("Installing " + fix.getName() + " fix");
        Thread.sleep(fix.getTimeToInstall());
        System.out.println("Install " + fix.getName() + " fix");
        return fix.getName();
    }
}
