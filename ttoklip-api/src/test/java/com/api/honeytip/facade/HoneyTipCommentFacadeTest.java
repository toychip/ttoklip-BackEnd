//package com.api.honeytip.facade;
//
//import static org.assertj.core.api.SoftAssertions.assertSoftly;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.api.global.support.response.Message;
//import com.api.honeytip.application.HoneyTipCommentFacade;
//import com.domain.comment.application.CommentService;
//import com.domain.honeytip.domain.HoneyTipComment;
//import java.util.Optional;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//@SuppressWarnings("NonAsciiCharacters")
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//class HoneyTipCommentFacadeTest extends HoneyTipFacadeTestHelper {
//
//    @InjectMocks
//    private HoneyTipCommentFacade honeyTipCommentFacade;
//
//    @Mock
//    private CommentService commentService;
//
//    /* -------------------------------------------- COMMENT CREATE TEST -------------------------------------------- */
//
//    @Test
//    void 최상위_댓글_등록_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        var honeyTip = HoneyTipFixture.본인_허니팁_생성(member);
//        Long postId = honeyTip.getId();
//        Long memberId = member.getId();
//        var request = CommentFixture.최상위_댓글_생성_요청();
//
//        when(commentService.findParentComment(any())).thenReturn(Optional.empty());
//        when(honeyTipPostService.getHoneytip(postId)).thenReturn(honeyTip);
//        when(memberService.getById(memberId)).thenReturn(member);
//
//        // When
//        Message result = honeyTipCommentFacade.register(postId, request, memberId);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("댓글");
//            softly.assertThat(result.getMessage()).contains("생성");
//        });
//
//        verify(commentService).register(any(HoneyTipComment.class));
//    }
//
//    @Test
//    void 대댓글_등록_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        var honeyTip = HoneyTipFixture.본인_허니팁_생성(member);
//        Long postId = honeyTip.getId();
//        Long memberId = member.getId();
//        var parentComment = CommentFixture.꿀팁_최상위_댓글_생성(honeyTip);
//
//        var request = CommentFixture.대댓글_생성_요청(parentComment);
//
//        when(commentService.findParentComment(parentComment.getId())).thenReturn(Optional.of(parentComment));
//        when(honeyTipPostService.getHoneytip(postId)).thenReturn(honeyTip);
//        when(memberService.findById(memberId)).thenReturn(member);
//
//        // When
//        Message result = honeyTipCommentFacade.register(postId, request, memberId);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("댓글");
//            softly.assertThat(result.getMessage()).contains("생성");
//        });
//
//        verify(commentService).register(any(HoneyTipComment.class));
//    }
//
//    /* -------------------------------------------- COMMENT CREATE TEST END -------------------------------------------- */
//
//
//    /* -------------------------------------------- COMMENT DELETE TEST -------------------------------------------- */
//    @Test
//    void 댓글_삭제_메서드_호출_성공() {
//        // Given
//        Long commentId = 100L;
//
//        // When
//        Message result = honeyTipCommentFacade.delete(commentId);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("삭제");
//        });
//
//        verify(commentService).deleteById(commentId);
//    }
//
//    /* -------------------------------------------- COMMENT DELETE TEST END -------------------------------------------- */
//
//
//    /* -------------------------------------------- COMMENT REPORT TEST -------------------------------------------- */
//    @Test
//    void 댓글_신고_메서드_호출_성공() {
//        // Given
//        Long commentId = 100L;
//
//        Member member = MemberFixture.일반_회원_생성1();
//        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_생성(member);
//        var comment = CommentFixture.꿀팁_최상위_댓글_생성(honeyTip);
//        var reportRequest = ReportFixture.신고_요청_픽스처();
//
//        when(commentService.findComment(commentId)).thenReturn(comment);
//
//        // When
//        Message result = honeyTipCommentFacade.report(commentId, reportRequest);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("신고");
//        });
//
//        verify(reportService).reportComment(reportRequest, comment);
//    }
//
//    /* -------------------------------------------- COMMENT REPORT TEST END -------------------------------------------- */
//}