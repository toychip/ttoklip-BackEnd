package com.api.ttoklip.domain.town.cart.dto.response;

import com.api.ttoklip.domain.town.cart.comment.dto.response.CartCommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CartResponse {

    private Long id;
    private String title;
    private String content;
    private String location;
    private String createdBy;

    private String chatLink;
    private String productLink;

    private int party;

    private int totalPrice;
    private int personPrice;

    private LocalDateTime deadline;

    private List<CartCommentResponse> cartCommentResponses;
}
