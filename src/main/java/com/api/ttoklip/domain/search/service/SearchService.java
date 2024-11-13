package com.api.ttoklip.domain.search.service;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.repository.scrap.HoneyTipSearchRepository;
import com.api.ttoklip.domain.mypage.dto.response.UserCartSingleResponse;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.repository.domain.NewsletterRepository;
import com.api.ttoklip.domain.search.response.CartPaging;
import com.api.ttoklip.domain.search.response.CommunityPaging;
import com.api.ttoklip.domain.search.response.TownCommunityResponse;
import com.api.ttoklip.domain.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.search.response.NewsletterPaging;
import com.api.ttoklip.domain.search.response.SingleResponse;
import com.api.ttoklip.domain.town.cart.domain.Cart;
import com.api.ttoklip.domain.town.cart.repository.post.CartSearchRepository;
import com.api.ttoklip.domain.town.community.domain.Community;
import com.api.ttoklip.domain.town.community.infrastructure.CommunitySearchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final HoneyTipSearchRepository honeyTipSearchRepository;
    private final CommunitySearchRepository communitySearchRepository;
    private final NewsletterRepository newsletterRepository;
    private final CartSearchRepository cartSearchRepository;

    public HoneyTipPaging honeyTipSearch(final String keyword, final Pageable pageable, final String sort) {
        Page<HoneyTip> contentPaging = honeyTipSearchRepository.getContain(keyword, pageable, sort);

        // List<Entity>
        List<HoneyTip> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<SingleResponse> honeyTipSingleData = contents.stream()
                .map(SingleResponse::honeyTipFrom)
                .toList();

        return HoneyTipPaging.builder()
                .honeyTips(honeyTipSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public NewsletterPaging newsletterPaging(final String keyword, final Pageable pageable, final String sort) {
        Page<Newsletter> contentPaging = newsletterRepository.getContain(keyword, pageable, sort);

        // List<Entity>
        List<Newsletter> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<SingleResponse> newsletterSingleData = contents.stream()
                .map(SingleResponse::newsletterFrom)
                .toList();

        return NewsletterPaging.builder()
                .newsletters(newsletterSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public CommunityPaging communityPaging(final String keyword, final Pageable pageable, final String sort) {
        Page<Community> contentPaging = communitySearchRepository.getContain(keyword, pageable, sort);

        // List<Entity>
        List<Community> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<TownCommunityResponse> communitySingleData = contents.stream()
                .map(TownCommunityResponse::from)
                .toList();

        return CommunityPaging.builder()
                .communities(communitySingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public CartPaging cartPaging(final String keyword, final Pageable pageable, final String sort) {
        Page<Cart> contentPaging = cartSearchRepository.getContain(keyword, pageable, sort);

        // List<Entity>
        List<Cart> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<UserCartSingleResponse> cartSingleData = contents.stream()
                .map(UserCartSingleResponse::from)
                .toList();

        return CartPaging.builder()
                .carts(cartSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }
}
