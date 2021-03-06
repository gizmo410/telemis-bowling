package com.telemis.bowling.query.notification;

import org.aspectj.lang.JoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static com.telemis.bowling.query.DataFixtures.GAME_CREATED;
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

        notificationAspect.afterQuerymodelUpdate(mockJoinpoint, GAME_CREATED);

        final String expectedDestination = "/topic/domain-events";
        verify(messagingTemplate).convertAndSend(eq(expectedDestination), eq(GAME_CREATED), anyMapOf(String.class, Object.class));
        verifyNoMoreInteractions(messagingTemplate);


    }
}
