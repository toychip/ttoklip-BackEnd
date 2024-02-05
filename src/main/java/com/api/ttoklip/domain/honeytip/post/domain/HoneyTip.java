package com.api.ttoklip.domain.honeytip.post.domain;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.base.BaseEntity;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.image.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.post.dto.request.HoneyTipCreateReq;
import com.api.ttoklip.domain.honeytip.post.editor.HoneyTipPostEditor;
import com.api.ttoklip.domain.honeytip.post.editor.HoneyTipPostEditor.HoneyTipPostEditorBuilder;
import com.api.ttoklip.domain.honeytip.url.domain.HoneyTipUrl;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HoneyTip extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipImage> honeyTipImageList = new ArrayList<>();

    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipUrl> honeyTipUrlList = new ArrayList<>();

    @OneToMany(mappedBy = "honeyTip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoneyTipComment> honeyTipComments = new ArrayList<>();

    public static HoneyTip from(final HoneyTipCreateReq req) {
        return HoneyTip.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .category(req.getCategory())
                .build();
    }

    public HoneyTipPostEditorBuilder toEditor() {
        return HoneyTipPostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(final HoneyTipPostEditor editor) {
        this.title = editor.getTitle();
        this.content = editor.getContent();
    }

    @Override
    public void deactivate() {
        super.deactivate(); // HoneyTip 엔티티 비활성화
        deactivateHoneyTipUrls(); // HoneyTipUrl 엔티티들을 비활성화
        deactivateHoneyTipImages(); // HoneyTipImage 엔티티들을 비활성화
    }

    private void deactivateHoneyTipUrls() {
        honeyTipUrlList.forEach(BaseEntity::deactivate);
    }

    private void deactivateHoneyTipImages() {
        honeyTipImageList.forEach(BaseEntity::deactivate);
    }

}