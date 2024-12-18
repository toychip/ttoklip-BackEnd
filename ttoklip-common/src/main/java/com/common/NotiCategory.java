package com.common;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotiCategory {

    HONEY_TIP_SCRAP("꿀팁 공유해요", "누군가가 글을 스크랩 했어요", true, false, "HoneyTip"),
    HONEY_TIP_LIKE("꿀팁 공유해요", "누군가에게 글이 도움이 되었대요", true, false, "HoneyTip"),
    HONEY_TIP_COMMENT("꿀팁 공유해요", "누군가가 댓글을 남겼어요", true, false, "Comment"),
    HONEY_TIP_CHILD_COMMENT("꿀팁 공유해요", "누군가 답글을 남겼어요", true, true, "Comment"),

    QUESTION_COMMENT_LIKE("질문해요", "누군가에게 남겨준 댓글이 도움이 되었대요", false, true, "Comment"),
    QUESTION_COMMENT("질문해요", "누군가가 댓글을 남겼어요", true, false, "Comment"),
    QUESTION_CHILD_COMMENT("질문해요", "누군가가 답글을 남겼어요", true, true, "Comment"),

    NEWS_LETTER_CHILD_COMMENT("뉴스레터", "누군가가 답글을 남겼어요", false, true, "Comment"),

    OUR_TOWN_SCRAP("우리동네 스크랩", "누군가가 글을 스크랩 했어요", true, false, "Community"),
    OUR_TOWN_COMMENT("우리동네 댓글", "누군가가 댓글을 남겼어요", true, false, "Comment"),
    OUR_TOWN_CHILD_COMMENT("우리동네 답글", "누군가가 답글을 남겼어요", true, true, "Comment"),
    OUR_TOWN_TOGETHER("우리동네 함께해요", "누군가가 함께하기에 참여했어요", true, false, "Cart"),

    BAD_TYPE_NOTIFICATION("알림 타입 없음", "해당 알림이 반환되면 안됩니다.", false, false, null),
    ;

    private final String title;
    private final String text;
    private final boolean notifyPostWriter;
    private final boolean notifyCommentWriter;
    private final String target;  // 도메인 클래스 대신 문자열로 변경

    public static NotiCategory getNotificationByCategory(final String value) {
        try {
            return NotiCategory.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorType.BAD_CATEGORY_NOTIFICATION);
        }
    }

}
