package com.telemis.bowling.query.notification;


import com.google.common.collect.ImmutableMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

/**
 * Aspect that aims to provide notification messages.
 */
@Aspect
public class NotificationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationAspect.class);
    private static final String QUEUE_DOMAIN_EVENTS = "/topic/domain-events";

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * Sends a notification message whenever an event updater with the @Notification annotation has been executed successfully.
     *
     * @param joinPoint    The matching joinpoint.
     * @param event        The event that has been passed as argument to the event updater.
     * @param registeredBy The user identifier that has been passed as argument to the event updater.
     */
    @AfterReturning(
            pointcut = "execution(* com.telemis.bowling.query..*(..) ) && @annotation(Notification) && args(event,registeredBy)",
            argNames = "joinPoint,event,registeredBy")
    public void afterQuerymodelUpdate(JoinPoint joinPoint, Object event, String registeredBy) {
        LOGGER.debug("pushing something to the client:" + joinPoint.toLongString());
        messagingTemplate.convertAndSendToUser(registeredBy, QUEUE_DOMAIN_EVENTS, event, createParameters(event));
    }

    private ImmutableMap<String, Object> createParameters(Object event) {
        return ImmutableMap.<String, Object>of("clazz", event.getClass().getSimpleName());
    }

}
