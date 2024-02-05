package com.api.ttoklip.domain.town.cart.post.entity;

import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.itemUrl.entity.ItemUrl;
import com.api.ttoklip.domain.town.cart.post.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.image.entity.CartImage;
import com.api.ttoklip.domain.town.cart.post.editor.CartPostEditor;
import com.api.ttoklip.domain.town.cart.post.editor.CartPostEditor.CartPostEditorBuilder;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    // todo 위치 기반으로
    private String location;

    private Long totalPrice;

    private String chatUrl;

    private Long party;

    @Builder
    public Cart(String title, String content, String location, Long totalPrice, String chatUrl, Long party) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.totalPrice = totalPrice;
        this.chatUrl = chatUrl;
        this.party = party;
    }

    public static Cart of(final CartCreateRequest request) {
        return Cart.builder()
                .content(request.getContent())
                .title(request.getTitle())
                .build();
    }

    @Override
    public void deactivate() {
        super.deactivate();  // BaseEntity에 정의되어 있는 메소드
        this.cartImages.forEach(CartImage::deactivate);
        this.reports.forEach(Report::deactivate);
        this.cartComments.forEach(CartComment::deactivate);
        this.itemUrls.forEach(ItemUrl::deactivate);
    }


    @Builder.Default
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CartImage> cartImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CartComment> cartComments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ItemUrl> itemUrls = new ArrayList<>();

    public CartPostEditorBuilder toEditor() {
        return CartPostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final CartPostEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }
}
