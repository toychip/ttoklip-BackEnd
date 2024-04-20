package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.notification.entity.CommentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationDispatcher notificationDispatcher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processCommentNotification(CommentCreatedEvent event) {
        log.info("----- NotificationService.processCommentNotification call");
        Comment comment = event.getComment();
        notificationDispatcher.dispatchCommentNotification(comment);
    }
}
