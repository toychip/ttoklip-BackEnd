package com.api.ttoklip.domain.honeytip.post.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordCreate;
import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordUpdate;
import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.comment.service.HoneyTipCommentService;
import com.api.ttoklip.domain.honeytip.image.service.HoneyTipImageService;
import com.api.ttoklip.domain.honeytip.like.service.HoneyTipLikeService;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.dto.request.HoneyTipCreateRequest;
import com.api.ttoklip.domain.honeytip.post.dto.request.HoneyTipEditReq;
import com.api.ttoklip.domain.honeytip.post.dto.response.HoneyTipSingleResponse;
import com.api.ttoklip.domain.honeytip.post.editor.HoneyTipPostEditor;
import com.api.ttoklip.domain.honeytip.scrap.service.HoneyTipScrapService;
import com.api.ttoklip.domain.honeytip.url.service.HoneyTipUrlService;
import com.api.ttoklip.domain.main.dto.response.CategoryPagingResponse;
import com.api.ttoklip.domain.main.dto.response.CategoryResponses;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.global.success.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipPostFacade {

    private final HoneyTipPostService honeyTipPostService;
    private final HoneyTipCommentService honeyTipCommentService;

    private final HoneyTipImageService honeyTipImageService;
    private final HoneyTipScrapService honeyTipScrapService;
    private final HoneyTipLikeService honeyTipLikeService;
    private final HoneyTipUrlService honeyTipUrlService;

    private final ReportService reportService;

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    @CheckBadWordCreate
    public Message register(final HoneyTipCreateRequest request) {
        Member currentMember = getCurrentMember();
        HoneyTip honeytip = HoneyTip.of(request, currentMember);
        honeyTipPostService.saveHoneyTipPost(honeytip);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(honeytip, uploadImages);
        }

        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            registerUrls(honeytip, urls);
        }

        return Message.registerPostSuccess(HoneyTip.class, honeytip.getId());
    }

    private void registerImages(final HoneyTip honeytip, final List<MultipartFile> uploadImages) {
        // S3에 이미지 업로드 후 URL 목록을 가져온다.
        List<String> uploadUrls = honeyTipPostService.uploadImages(uploadImages);
        uploadUrls.forEach(uploadUrl -> honeyTipImageService.register(honeytip, uploadUrl));
    }

    private void registerUrls(final HoneyTip honeytip, final List<String> urls) {
        urls.forEach(url -> honeyTipUrlService.register(honeytip, url));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */
    @Transactional
    @CheckBadWordUpdate
    public Message edit(final Long postId, final HoneyTipEditReq request) {

        // 기존 게시글 찾기
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(postId);
        honeyTipPostService.checkEditPermission(honeyTip);

        // title, content 수정
        HoneyTipPostEditor postEditor = getPostEditor(request, honeyTip);
        honeyTip.edit(postEditor);

        // url 수정
        List<String> urls = request.getUrl();
        if (urls != null && !urls.isEmpty()) {
            // URL 리스트 수정
            honeyTipUrlService.updateHoneyTipUrls(honeyTip, urls);
        }

        // 이미지 추가
        List<MultipartFile> addImages = request.getAddImages();
        if (addImages != null && !addImages.isEmpty()) {
            registerImages(honeyTip, addImages);
        }

        // 이미지 삭제
        List<Long> deleteImageIds = request.getDeleteImageIds();
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            deleteImages(deleteImageIds);
        }

        return Message.editPostSuccess(HoneyTip.class, honeyTip.getId());
    }

    private HoneyTipPostEditor getPostEditor(final HoneyTipEditReq request, final HoneyTip honeyTip) {
        HoneyTipPostEditor.HoneyTipPostEditorBuilder editorBuilder = honeyTip.toEditor();
        HoneyTipPostEditor honeyTipPostEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return honeyTipPostEditor;
    }

    private void deleteImages(final List<Long> deleteImageIds) {
        honeyTipImageService.deleteImages(deleteImageIds);
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- DELETE -------------------------------------------- */
    @Transactional
    public Message delete(final Long postId) {
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(postId);

        honeyTipPostService.checkEditPermission(honeyTip);
        honeyTip.deactivate();

        return Message.deletePostSuccess(HoneyTip.class, postId);
    }

    /* -------------------------------------------- DELETE 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request) {
        HoneyTip honeytip = honeyTipPostService.getHoneytip(postId);
        reportService.reportHoneyTip(request, honeytip);

        return Message.reportPostSuccess(HoneyTip.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- 단건 READ -------------------------------------------- */
    public HoneyTipSingleResponse getSinglePost(final Long postId) {
        HoneyTip honeyTipWithImgAndUrl = honeyTipPostService.findHoneyTipWithDetails(postId);
        List<HoneyTipComment> activeComments = honeyTipCommentService.findCommentsByHoneyTipId(postId);

        int likeCount = honeyTipLikeService.countHoneyTipLikes(postId).intValue();
        int scrapCount = honeyTipScrapService.countHoneyTipScraps(postId).intValue();

        // 현재 사용자가 좋아요를 눌렀는지 확인
        boolean likedByCurrentUser = honeyTipLikeService.existsByHoneyTipIdAndMemberId(postId);
        boolean scrapedByCurrentUser = honeyTipScrapService.existsByHoneyTipIdAndMemberId(postId);

        HoneyTipSingleResponse honeyTipSingleResponse = HoneyTipSingleResponse.of(honeyTipWithImgAndUrl,
                activeComments, likeCount, scrapCount, likedByCurrentUser, scrapedByCurrentUser);
        return honeyTipSingleResponse;
    }

    /* -------------------------------------------- 단건 READ 끝 -------------------------------------------- */


    /* -------------------------------------------- 카토고리별 MAIN READ -------------------------------------------- */
    public CategoryResponses getDefaultCategoryRead() {
        List<HoneyTip> houseWorkQuestions = honeyTipPostService.findHouseworkTips();
        List<HoneyTip> recipeQuestions = honeyTipPostService.findRecipeTips();
        List<HoneyTip> safeLivingQuestions = honeyTipPostService.findSafeLivingTips();
        List<HoneyTip> welfarePolicyQuestions = honeyTipPostService.findWelfarePolicyTips();

        return CategoryResponses.builder()
                .housework(convertToTitleResponses(houseWorkQuestions))
                .cooking(convertToTitleResponses(recipeQuestions))
                .safeLiving(convertToTitleResponses(safeLivingQuestions))
                .welfarePolicy(convertToTitleResponses(welfarePolicyQuestions))
                .build();
    }

    private List<TitleResponse> convertToTitleResponses(final List<HoneyTip> honeyTips) {
        return honeyTips.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();
    }

    public List<TitleResponse> getPopularityTop5() {
        List<HoneyTip> top5HoneyTips = honeyTipPostService.getPopularityTop5();
        return top5HoneyTips.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();
    }

    /* -------------------------------------------- 카토고리별 MAIN READ 끝 -------------------------------------------- */


    /* -------------------------------------------- 좋아요 추가 & 취소 -------------------------------------------- */
    @Transactional
    public Message registerLike(final Long postId) {
        honeyTipLikeService.register(postId);
        return Message.likePostSuccess(HoneyTip.class, postId);
    }

    @Transactional
    public Message cancelLike(final Long postId) {
        honeyTipLikeService.cancel(postId);
        return Message.likePostCancel(HoneyTip.class, postId);
    }

    /* -------------------------------------------- 좋아요 추가 & 취소 끝 -------------------------------------------- */


    /* -------------------------------------------- 카테고리별 페이징 -------------------------------------------- */
    public CategoryPagingResponse matchCategoryPaging(final Category category, final Pageable pageable) {
        Page<HoneyTip> questions = honeyTipPostService.matchCategoryPaging(category, pageable);

        List<TitleResponse> data = questions.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();

        return CategoryPagingResponse.builder()
                .data(data)
                .category(category)
                .totalPage(questions.getTotalPages())
                .totalElements(questions.getTotalElements())
                .isLast(questions.isLast())
                .isFirst(questions.isFirst())
                .build();
    }

    /* -------------------------------------------- 카테고리별 페이징 끝 -------------------------------------------- */


    /* -------------------------------------------- 스크랩 추가 & 취소 -------------------------------------------- */
    @Transactional
    public Message registerScrap(Long postId) {
        honeyTipScrapService.register(postId);
        return Message.scrapPostSuccess(HoneyTip.class, postId);
    }

    @Transactional
    public Message cancelScrap(Long postId) {
        honeyTipScrapService.cancelScrap(postId);
        return Message.scrapPostCancel(HoneyTip.class, postId);
    }

    public List<TitleResponse> getRecent3() {
        List<HoneyTip> recent3HoneyTip = honeyTipPostService.findRecent3();
        return recent3HoneyTip.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();
    }
    /* -------------------------------------------- 스크랩 추가 & 취소 끝 -------------------------------------------- */

}
