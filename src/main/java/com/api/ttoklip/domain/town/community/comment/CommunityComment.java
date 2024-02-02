package com.api.ttoklip.domain.town.community.comment;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Community")
public class CommunityComment extends Comment {

    @Builder
    private CommunityComment(String content, Comment parent, Community community) {
        super(content, parent, community); // Comment 클래스의 생성자 호출
    }

    public static CommunityComment withParentOf(final CommentCreateRequest request, final Comment parent,
                                      final Community community) {
        return CommunityComment.builder()
                .content(request.getComment())
                .parent(parent)
                .community(community)
                .build();
    }

    public static CommunityComment orphanageOf(final CommentCreateRequest request, final Community community) {
        return CommunityComment.builder()
                .content(request.getComment())
                .parent(null)
                .community(community)
                .build();
    }
}

