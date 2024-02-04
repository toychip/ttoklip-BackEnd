package com.api.ttoklip.domain.honeytip.post.repository;


import static com.api.ttoklip.domain.honeytip.comment.domain.QHoneyTipComment.honeyTipComment;
import static com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip.honeyTip;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipDefaultRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<HoneyTip> getHouseWork() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .where(honeyTip.category.eq(Category.HOUSEWORK))
                .limit(10)
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    public List<HoneyTip> getRecipe() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .where(honeyTip.category.eq(Category.RECIPE))
                .limit(10)
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    public List<HoneyTip> getSafeLiving() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .where(honeyTip.category.eq(Category.SAFE_LIVING))
                .limit(10)
                .orderBy(honeyTip.id.desc())
                .fetch();
    }

    public List<HoneyTip> getWelfarePolicy() {
        return jpaQueryFactory
                .selectFrom(honeyTip)
                .leftJoin(honeyTip.honeyTipComments, honeyTipComment)
                .fetchJoin()
                .where(honeyTip.category.eq(Category.WELFARE_POLICY))
                .limit(10)
                .orderBy(honeyTip.id.desc())
                .fetch();
    }
}
