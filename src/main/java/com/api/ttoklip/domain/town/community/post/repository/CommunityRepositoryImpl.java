package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.entity.QCommunity;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import static com.api.ttoklip.domain.town.community.comment.QCommunityComment.communityComment;
import static com.api.ttoklip.domain.town.community.image.entity.QCommunityImage.communityImage;
import static com.api.ttoklip.domain.town.community.post.entity.QCommunity.community;


@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Community findByIdActivated(final Long communityId) {
        Community findCommunity = jpaQueryFactory
                .selectFrom(community)
                .where(
                        matchId(communityId), getCommunityActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findCommunity)
                .orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long communityId) {
        return community.id.eq(communityId);
    }

    private BooleanExpression getCommunityActivate() {
        return community.deleted.isFalse();
    }

    @Override
    public Community findByIdFetchJoin(final Long communityPostId) {
        Community findCommunity = jpaQueryFactory
                .selectFrom(community)
                .leftJoin(community.communityImages, communityImage)
                .fetchJoin()
                .where(
                        getCommunityActivate(),
                        community.id.eq(communityPostId)
                )
                .fetchOne();

        return Optional.ofNullable(findCommunity)
                .orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_NOT_FOUND));
    }

    @Override
    public List<CommunityComment> findActiveCommentsByCommunityId(final Long communityId) {
        return jpaQueryFactory
                .selectFrom(communityComment)
                .where(
                        matchCommunityId(communityId),
                        getCommentActivate()
                )
                .orderBy(
                        communityComment.createdDate.asc(),
                        communityComment.parent.id.asc().nullsFirst()
                )
                .fetch();
    }

    private BooleanExpression matchCommunityId(final Long communityId) {
        return community.id.eq(communityId);
    }

    private BooleanExpression getCommentActivate() {
        return communityComment.deleted.isFalse();
    }
}