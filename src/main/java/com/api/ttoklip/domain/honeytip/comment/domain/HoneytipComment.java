package com.api.ttoklip.domain.honeytip.comment.domain;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.honeytip.post.post.domain.Honeytip;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Honeytip")
public class HoneytipComment extends Comment {

    public static HoneytipComment withParentOf(final CommentCreateRequest request, final Comment parent,
                                               final Honeytip honeytip) {
        return HoneytipComment.builder()
                .content(request.getComment())
                .parent(parent)
                .honeytip(honeytip)
                .build();
    }

    public static HoneytipComment orphanageOf(final CommentCreateRequest request, final Honeytip honeytip) {
        return HoneytipComment.builder()
                .content(request.getComment())
                .parent(null)
                .honeytip(honeytip)
                .build();
    }
}