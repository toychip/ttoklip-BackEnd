package com.api.ttoklip.domain.manager.inquiry.controller;

import com.api.ttoklip.domain.manager.inquiry.constant.InquiryConstant;
import com.api.ttoklip.domain.manager.inquiry.dto.request.FaqCreateRequest;
import com.api.ttoklip.domain.manager.inquiry.dto.request.InquiryCreateRequest;
import com.api.ttoklip.domain.manager.inquiry.dto.response.FaqPaging;
import com.api.ttoklip.domain.manager.inquiry.dto.response.InquiryPaging;
import com.api.ttoklip.domain.manager.inquiry.dto.response.InquiryResponse;
import com.api.ttoklip.domain.manager.inquiry.service.InquiryService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "문의하기", description = "문의하기 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inquiry")
public class InquiryController {
    private final static int PAGE_SIZE = 10; // 페이지 당 데이터 수
    private final InquiryService inquiryService;

    @Operation(summary = "모든 문의사항 불러오기", description = "문의사항 목록을 가져옵니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문의사항 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = InquiryConstant.inquiryListResponse,
                                    description = "전체 문의사항 목록이 조회되었습니다"
                            )))})
    @GetMapping()
    public SuccessResponse<InquiryPaging> getInquiryList(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        InquiryPaging inquiryPaging = inquiryService.getInquiryList(pageable);
        return new SuccessResponse<>(inquiryPaging);

    }

    @Operation(summary = "모든 FaQ 불러오기", description = "FaQ 목록을 가져옵니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FaQ 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = InquiryConstant.faqResponse,
                                    description = "FaQ가 조회되었습니다"
                            )))})
    @GetMapping("/faq")
    public SuccessResponse<FaqPaging> getFaQList(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        FaqPaging faqPaging = inquiryService.getFaqList(pageable);
        return new SuccessResponse<>(faqPaging);

    }
    @Operation(summary = "자주묻는 문의", description = "FaQ를 생성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FaQ 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = InquiryConstant.faqRegisterResponse,
                                    description = "FaQ 생성에 성공하였습니다"
                            )))})
    @PostMapping(value = "/faq/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse<Message> faqRegister(final @Validated @RequestBody FaqCreateRequest request) {
        Message message = inquiryService.faqRegister(request);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "문의하기", description = "문의한 내용을 생성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문의 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = InquiryConstant.inquiryRegisterResponse,
                                    description = "문의에 성공하였습니다"
                            )))})
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse<Message> register(final @Validated @RequestBody InquiryCreateRequest request) {
        Message message = inquiryService.register(request);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "문의하기 조회", description = "문의하기 ID에 해당하는 문의사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문의사항 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = InquiryConstant.inquirySingleResponse,
                                    description = "문의하기 단건 조회되었습니다."
                            )))})
    @GetMapping("/{inquiryId}")
    public SuccessResponse<InquiryResponse> getSingleInquiry(final @PathVariable Long inquiryId) {
        InquiryResponse response = inquiryService.getSingleInquiry(inquiryId);
        return new SuccessResponse<>(response);
    }

    @Operation(summary = "문의사항 삭제", description = "문의사항 ID에 해당하는 문의사항을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문의사항 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = InquiryConstant.inquiryDeleteResponse,
                                    description = "문의사항을 삭제하였습니다"
                            )))})
    @DeleteMapping("/{inquiryId}")
    public SuccessResponse<Message> deleteInquiry(final @PathVariable Long inquiryId) {
        Message message = inquiryService.deleteInquiry(inquiryId);
        return new SuccessResponse<>(message);
    }

}
