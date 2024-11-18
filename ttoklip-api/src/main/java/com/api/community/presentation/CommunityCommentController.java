package com.api.community.presentation;

import com.api.common.ReportWebCreate;
import com.api.community.application.CommunityCommentFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.domain.comment.domain.CommentCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Town", description = "우리동네 - 소통해요 댓글 API 입니다.")
@RestController
@RequestMapping("api/v1/town/comms/comment")
@RequiredArgsConstructor
public class CommunityCommentController {

    private final CommunityCommentFacade communityCommentFacade;

    // 소통해요(community) 댓글

    /* CREATE */
    @Operation(summary = "소통해요 댓글 생성", description = "소통해요 게시글에 댓글을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "소통해요 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class)
                    ))})
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(final @PathVariable Long postId,
                                             final @RequestBody CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    /* REPORT */
    @Operation(summary = "소통해요 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "소통해요 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class)
                    ))})
    @PostMapping("/report/{commentId}")
    public TtoklipResponse<Message> report(final @PathVariable Long commentId,
                                           final @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    /* DELETE */
    @Operation(summary = "댓글 삭제", description = "지정된 게시글에 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "질문 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class)
                    ))})
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(final @PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}