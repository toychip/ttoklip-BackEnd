package com.api.ttoklip.domain.honeytip.comment.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.common.comment.service.CommentService;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneytipComment;
import com.api.ttoklip.domain.honeytip.post.post.domain.Honeytip;
import com.api.ttoklip.domain.honeytip.post.post.service.HoneytipPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneytipCommentService {

    private final HoneytipPostService honeytipPostService;
    private final CommentService commentService;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Long register(final Long postId, final CommentCreateRequest request) {
        return null;
        Honeytip findHoneytip = honeytipPostService.findHoneytipById(postId);

        // comment 부모 찾기
        Long parentCommentId = request.getParentCommentId();
        Optional<Comment> parentCommentOptional = commentService.findParentComment(parentCommentId);

        // 부모 댓글이 존재한다면
        if (parentCommentOptional.isPresent()) {
            Comment parentComment = parentCommentOptional.get();
            return registerCommentWithParent(request, findHoneytip, parentComment);
        }

        // 최상위 댓글 생성
        return registerCommentOrphanage(request, findHoneytip);
    }

    // 대댓글 생성
    private Long registerCommentWithParent(final CommentCreateRequest request, final Honeytip findHoneytip,
                                           final Comment parentComment) {
        HoneytipComment newHoneytipComment = HoneytipComment.withParentOf(request, parentComment, findHoneytip);
        commentService.register(newHoneytipComment);
        return newHoneytipComment.getId();
    }

    // 최상위 댓글 생성
    private Long registerCommentOrphanage(final CommentCreateRequest request, final Honeytip findHoneytip) {
        HoneytipComment newHoneytipComment = HoneytipComment.orphanageOf(request, findHoneytip);
        commentService.register(newHoneytipComment);
        return newHoneytipComment.getId();
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- UPDATE -------------------------------------------- */
    @Transactional
    public void edit(final Long commentId, final CommentEditRequest request) {
        commentService.edit(commentId, request);
    }

    /* -------------------------------------------- UPDATE 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public void delete(final Long commentId) {
        // ToDo 본인이 썼는지 검증 과정 필요
        commentService.deleteById(commentId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */

}
