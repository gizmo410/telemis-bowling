package com.telemis.bowling.query.notification;

import be.milieuinfo.midas.domain.api.controle.event.ControleAangemaakt;
import org.aspectj.lang.JoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @since 18/06/14
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationAspectTest {

    @Mock
    private SimpMessageSendingOperations messagingTemplate;

    @InjectMocks
    private NotificationAspect notificationAspect;

    @Test
    public void assertThat_when_afterQueryModelUpdateIsCalled_then_theGivenEventIsCorrectlySent() {

        final JoinPoint mockJoinpoint = mock(JoinPoint.class);

        final ControleAangemaakt controleAangemaaktEvent = ControleAangemaakt.newBuilder().build();

        final String registeredBy = "Anakin Skywalker";

        notificationAspect.afterQuerymodelUpdate(mockJoinpoint, controleAangemaaktEvent, registeredBy);

        final String expectedDestination = "/topic/domain-events";
        verify(messagingTemplate).convertAndSendToUser(eq(registeredBy), eq(expectedDestination), eq(controleAangemaaktEvent), anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(messagingTemplate);


    }
}
