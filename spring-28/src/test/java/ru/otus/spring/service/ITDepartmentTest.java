package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.config.IntegrationConfig;
import ru.otus.spring.domain.Bug;
import ru.otus.spring.domain.Fix;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {IntegrationConfig.class, JuniorProgrammer.class, SeniorProgrammer.class,
        DevOpsEngineer.class})
class ITDepartmentTest {

    @Autowired
    private MessageChannel programChannel;
    @MockBean
    private JuniorProgrammer juniorProgrammer;
    @MockBean
    private SeniorProgrammer seniorProgrammer;
    @MockBean
    private DevOpsEngineer devOpsEngineer;

    @Test
    public void programChannelTest() throws InterruptedException {
        Bug infoBug = new Bug("info", 100);
        Bug criticalBug = new Bug("critical", 100);

        Fix infoFix = new Fix(infoBug.getName(), 100);
        Fix criticalFix = new Fix(criticalBug.getName(), 100);
        when(juniorProgrammer.fixBug(infoBug)).thenReturn(infoFix);
        when(seniorProgrammer.fixBug(criticalBug)).thenReturn(criticalFix);

        boolean isSend = programChannel.send(new GenericMessage<>(Arrays.asList(infoBug, criticalBug)));
        assertTrue(isSend);
        Thread.sleep(100);

        verify(juniorProgrammer).fixBug(infoBug);
        verify(seniorProgrammer).fixBug(criticalBug);
        verify(devOpsEngineer).installFix(infoFix);
        verify(devOpsEngineer).installFix(criticalFix);
    }
}