package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.notification.dto.response.NotificationFrontResponse;
import com.api.ttoklip.domain.notification.dto.response.NotificationFrontResponses;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.domain.notification.entity.Notification;
import com.api.ttoklip.domain.notification.repository.NotificationRepository;
import com.api.ttoklip.domain.notification.repository.NotificationRepositoryImpl;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationRepositoryImpl notificationRepositoryImpl;

    @Transactional
    public void register(final NotiCategory notiCategory, final Member member, final Long targetClassId,
                         final boolean status) {
        String title = notiCategory.getTitle();
        String text = notiCategory.getText();

        Object target = notiCategory.getTarget();
        String targetClassType = getTargetType(target);

        Notification notification = Notification.of(member, title, text, notiCategory, targetClassType, targetClassId,
                status);
        notificationRepository.save(notification);
    }

    private String getTargetType(Object target) {
        if (target instanceof Class) {
            Class<?> clazz = (Class<?>) target;

            if (clazz.equals(HoneyTip.class)) {
                return HoneyTip.class.getSimpleName();
            }

            if (clazz.equals(Community.class)) {
                return Community.class.getSimpleName();
            }

            if (clazz.equals(Cart.class)) {
                return Cart.class.getSimpleName();
            }

            if (clazz.equals(Comment.class)) {
                return Comment.class.getSimpleName();
            }
        }

        throw new ApiException(ErrorType._BAD_CATEGORY_NOTIFICATION_TYPE);
    }


    public NotificationFrontResponses findNotificationByCategory(final String value) {
        NotiCategory category = NotiCategory.getNotificationByCategory(value);
        List<Notification> top5RecentNotifications = notificationRepositoryImpl.findTop5RecentNotifications(category);

        List<NotificationFrontResponse> responses = top5RecentNotifications.stream()
                .map(noti -> NotificationFrontResponse.of(noti.getId(), noti.getTargetIndex(), noti.getTargetType(),
                        noti.getTitle(),
                        noti.getText(), noti.isStatus())).toList();

        return NotificationFrontResponses.from(responses);
    }
}
