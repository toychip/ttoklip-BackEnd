package com.api.ttoklip.domain.notification.aop;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.notification.aop.annotation.SendNotification;
import com.api.ttoklip.domain.notification.dto.request.NotificationRequest;
import com.api.ttoklip.domain.notification.service.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class NotificationAspect {

    private final NotificationDispatcher notificationDispatcher;

    @AfterReturning(value = "@annotation(notification)")
    public void afterScrapNotification(JoinPoint joinPoint, SendNotification notification) {
        log.info("[Notification] {}", joinPoint.getSignature());

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        // targetId
        Object argumentObj = joinPoint.getArgs()[0];
        Long targetIndex = (Long) argumentObj;

        notificationDispatcher.dispatchNotification(
                NotificationRequest.of(targetIndex, className, methodName)
        );
    }
}
