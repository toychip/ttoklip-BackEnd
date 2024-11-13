package com.domain.cart.domain;

import com.domain.common.vo.TownCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartRepository {
    Cart save(Cart cart);

    Cart findByIdActivated(final Long cartId);

    Cart findByIdFetchJoin(final Long postId);

    List<CartComment> findActiveCommentsByCartId(final Long postId);

    // 참여자 추가
    Cart addParticipant(final Long cartId);

    // 참여 취소
    Cart removeParticipant(final Long cartId);

    // 참여자 수 확인
    Long countParticipants(Long cartId);

    List<Cart> findRecent3(final TownCriteria townCriteria);

    Page<Cart> getCartPaging(
            final Pageable pageable,
            final Long startMoney,
            final Long lastMoney,
            final Long startParty,
            final Long lastParty,
            final TownCriteria townCriteria
    );

    Page<Cart> getContain(String keyword, Pageable pageable, String sort);
}
