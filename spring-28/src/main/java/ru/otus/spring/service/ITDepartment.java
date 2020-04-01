package ru.otus.spring.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.domain.Bug;

import java.util.Collection;

@MessagingGateway
public interface ITDepartment {

    @Gateway(requestChannel = "programChannel", replyChannel = "releaseChannel")
    String fixBug(Collection<Bug> bugs);

}
