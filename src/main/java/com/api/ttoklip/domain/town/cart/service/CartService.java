package com.api.ttoklip.domain.town.cart.service;

import com.api.ttoklip.domain.town.cart.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.dto.request.CartSearchCondition;
import com.api.ttoklip.domain.town.cart.dto.request.CartUpdateRequest;
import com.api.ttoklip.domain.town.cart.dto.response.CartListResponse;
import com.api.ttoklip.domain.town.cart.dto.response.CartResponse;
import com.api.ttoklip.domain.town.cart.dto.response.CartSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    // 더보기 페이지 조회
    public static CartListResponse searchCartPaging(final CartSearchCondition condition, final Pageable pageable) {
        return null;
    }

    public CartResponse getCart(final Long cartId) {
        return null;
    }

    public CartResponse createCartPost(final CartCreateRequest request) {
        return null;
    }

    public void updateCartPost(final Long cartId, final CartUpdateRequest request) {
        return;
    }

    public List<CartSummaryResponse> getAllCartsSummary() { return null;
    }
}