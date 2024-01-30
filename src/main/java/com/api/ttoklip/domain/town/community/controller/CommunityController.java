package com.api.ttoklip.domain.town.community.controller;

import com.api.ttoklip.domain.town.community.constant.CommunityResponseConstant;
import com.api.ttoklip.domain.town.community.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.dto.request.CommunitySearchCondition;
import com.api.ttoklip.domain.town.community.dto.request.CommunityUpdateRequest;
import com.api.ttoklip.domain.town.community.dto.response.CommunityListResponse;
import com.api.ttoklip.domain.town.community.dto.response.CommunityResponse;
import com.api.ttoklip.domain.town.community.service.CommunityService;
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
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@Tag(name = "Town", description = "우리동네 - 소통해요 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/town/comms")
public class CommunityController {

    private final CommunityService communityService;

    // 소통해요 - comm
    @Operation(summary = "소통해요 게시글 조회",
            description = "소통해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommunityResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.readSingleCommunity,
                                    description = "소통해요 게시글이 조회되었습니다."
                            )))})
    @GetMapping("/{commId}")
    public SuccessResponse<CommunityResponse> getComm(final @PathVariable Long commId) {
        CommunityResponse commResponse = communityService.getCommunity(commId);
        return new SuccessResponse<>(commResponse);
    }

    @Operation(summary = "소통해요 게시글 생성",
            description = "소통해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = CommunityResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.createAndDeleteCommunity,
                                    description = "소통해요 게시글이 생성되었습니다."
                            )))})
    @PostMapping
    public SuccessResponse<CommunityResponse> createCommPost(final @Validated @ModelAttribute CommunityCreateRequest request) {
        CommunityResponse commResponse = communityService.createCommunityPost(request);
        return new SuccessResponse<>(commResponse);
    }

    @Operation(summary = "소통해요 게시글 수정",
            description = "소통해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.updateCommunity,
                                    description = "소통해요 게시글이 수정되었습니다."
                            )))})
    @PatchMapping("/{commId}")
    public SuccessResponse<Long> updateCommPost(final @PathVariable Long commId,
                                                final @ModelAttribute CommunityUpdateRequest request) {
        communityService.updateCommunityPost(commId, request);
        return new SuccessResponse<>(commId);
    }

    @Operation(summary = "소통해요 게시글 삭제",
            description = "소통해요 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 삭제 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.createAndDeleteCommunity,
                                    description = "소통해요 게시글이 삭제되었습니다."
                            )))})
    @DeleteMapping("/{commId}")
    public SuccessResponse<Long> deleteCommPost(final @PathVariable Long commId) {
        communityService.deleteCommunityPost(commId);
        return new SuccessResponse<>(commId);
    }
}
