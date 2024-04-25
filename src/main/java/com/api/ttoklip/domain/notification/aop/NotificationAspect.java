package com.api.ttoklip.domain.notification.aop;

import com.api.ttoklip.domain.notification.aop.annotation.SendNotification;
import com.api.ttoklip.domain.notification.entity.PostETCEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class NotificationAspect {

    private final ApplicationEventPublisher eventPublisher;

    @AfterReturning(value = "@annotation(notification)")
    public void afterScrapNotification(JoinPoint joinPoint, SendNotification notification) {
        log.info("[Notification] {}", joinPoint.getSignature());

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        // targetId
        Object argumentObj = joinPoint.getArgs()[0];
        Long targetIndex = (Long) argumentObj;

        eventPublisher.publishEvent(new PostETCEvent(targetIndex, className, methodName));

//        notificationDispatcher.dispatchNotification(
//                NotificationRequest.of(targetIndex, className, methodName)
//        );
    }
}
