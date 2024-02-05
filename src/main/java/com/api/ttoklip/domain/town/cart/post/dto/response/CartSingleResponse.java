package com.api.ttoklip.domain.town.cart.post.dto.response;

import com.api.ttoklip.domain.common.comment.dto.response.CommentResponse;
import com.api.ttoklip.domain.question.image.dto.response.ImageResponse;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.image.dto.response.CartImageResponse;
import com.api.ttoklip.domain.town.cart.image.entity.CartImage;
import com.api.ttoklip.domain.town.cart.itemUrl.entity.ItemUrl;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartSingleResponse {

    @Schema(description = " ID", example = "1")
    private Long cartId;

    @Schema(description = "함께해요 제목", example = "함께해요 제목 예시")
    private String title;

    @Schema(description = "함께해요 내용", example = "함께해요 내용 예시")
    private String content;

    @Schema(description = "함께해요 작성자", example = "작성자 예시")
    private String writer;

    @Schema(description = "함께해요 총 가격", example = "총 가격 예시")
    private Long totalPrice;

    @Schema(description = "함께해요 거래희망 장소", example = "거래희망 장소 예시")
    private String location;

    @Schema(description = "함께해요 오픈채팅방 링크", example = "오픈채팅방 링크 예시")
    private String chatUrl;

    @Schema(description = "함께해요 공구 최대 인원", example = "공구 최대 인원 예시")
    private Long party;

    @Schema(description = "함께해요 작성 시간", example = "2024-01-15 10:00:00")
    private String writtenTime;

    @Schema(description = "함께해요에 포함된 이미지 URL 목록")
    private List<CartImageResponse> imageUrls;

    @Schema(description = "함께해요에 대한 댓글 목록")
    private List<CommentResponse> commentResponses;

    @Schema(description = "함께해요에 대한 상품관련 링크")
    private List<ItemUrl> itemUrls;

    public static CartSingleResponse of(final Cart cart, final List<CartComment> activeComments) {

        // 시간 포멧팅
        String formattedCreatedDate = getFormattedCreatedDate(cart);

        // CartImage entity to Response
        List<CartImageResponse> imageResponses = getImageResponses(cart);

        // Comment entity to Response
        List<CommentResponse> commentResponses = getCommentResponses(activeComments);

        return CartSingleResponse.builder()
                .cartId(cart.getId())
                .title(cart.getTitle())
                .content(cart.getContent())
//                .writer(cart.getMember().getName) ToDo Member 엔티티 연결 후 수정
                .writtenTime(formattedCreatedDate)
                .imageUrls(imageResponses)
                .commentResponses(commentResponses)
                .build();
    }

    private static String getFormattedCreatedDate(final Cart cart) {
        LocalDateTime createdDate = cart.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }

    private static List<CartImageResponse> getImageResponses(final Cart cart) {
        List<CartImage> cartImages = cart.getCartImages();
        return cartImages
                .stream()
                .map(CartImageResponse::from)
                .toList();
    }

    private static List<CommentResponse> getCommentResponses(final List<CartComment> cartComments) {
        return cartComments
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}