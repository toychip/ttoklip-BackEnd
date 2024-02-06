package com.api.ttoklip.domain.newsletter.post.controller;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.newsletter.main.constant.NewsletterResponseConstant;
import com.api.ttoklip.domain.newsletter.post.dto.request.NewsletterCreateReq;
import com.api.ttoklip.domain.newsletter.post.dto.response.NewsletterWithCommentRes;
import com.api.ttoklip.domain.newsletter.post.service.NewsletterPostService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Newsletter Post", description = "Newsletter Post API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/newsletter/posts")
public class NewsletterController {

    private final NewsletterPostService newsletterPostService;

    /* CREATE */
    @Operation(summary = "새로운 뉴스레터 생성", description = "form/data로 새로운 뉴스레터 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 게시글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.CREATE_NEWSLETTER,
                                    description = "뉴스레터가 생성되었습니다."
                            )))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Message> register(final @Validated @ModelAttribute NewsletterCreateReq request) {
        return new SuccessResponse<>(newsletterPostService.register(request));
    }

    /* READ */
    @Operation(summary = "뉴스레터 게시글 조회", description = "뉴스레터 개별 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 게시글 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.readSingleNewsletter,
                                    description = "뉴스레터가 조회되었습니다."
                            )))})
    @GetMapping("/{postId}")
    public SuccessResponse<NewsletterWithCommentRes> getSinglePost(final @PathVariable Long postId) {
        return new SuccessResponse<>(newsletterPostService.getSinglePost(postId));
    }

    /* REPORT */
    @Operation(summary = "뉴스레터 게시글 신고", description = "뉴스레터 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.REPORT_NEWSLETTER,
                                    description = "뉴스레터를 신고했습니다."
                            )))})
    @PostMapping("/report/{postId}")
    public SuccessResponse<Message> report(final @PathVariable Long postId,
                                           final @RequestBody ReportCreateRequest request) {
        Message message = newsletterPostService.report(postId, request);
        return new SuccessResponse<>(message);
    }
}