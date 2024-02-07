package com.api.ttoklip.domain.honeytip.post.repository;


import com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class HoneyTipSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QHoneyTip honeyTip = QHoneyTip.honeyTip;
    private final QHoneyTipComment honeyTipComment = QHoneyTipComment.honeyTipComment;


    public Page<HoneyTip> getContain(final String keyword, final Pageable pageable) {
        List<HoneyTip> content = getSearchPageContent(keyword, pageable);
        Long count = countQuery(keyword);
        return new PageImpl<>(content, pageable, count);
    }

    private List<HoneyTip> getSearchPageContent(final String keyword, final Pageable pageable) {
        return jpaQueryFactory
                .select(honeyTip)
                .from(honeyTip)
                .distinct()
                .where(
                        containContent(keyword)
                )
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    private BooleanExpression containContent(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return honeyTip.content.contains(keyword);
        }
        return null;
    }

    private Long countQuery(final String keyword) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTip)
                .distinct()
                .where(
                        containContent(keyword)
                )
                .fetchOne();
    }
}