package com.api.ttoklip.domain.stranger.controller;

import com.api.ttoklip.domain.mypage.main.constant.MyPageConstant;
import com.api.ttoklip.domain.mypage.main.dto.response.MyPageResponse;
import com.api.ttoklip.domain.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.stranger.constant.StrangerConstant;
import com.api.ttoklip.domain.stranger.dto.response.StrangerResponse;
import com.api.ttoklip.domain.stranger.service.StrangerService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Stranger", description = "타인의 프로필 조회 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stranger")
public class StrangerController {

    private final StrangerService strangerService;

    @Operation(summary = "타인 정보 불러오기", description = "타인의 기본 정보인 닉네임,동네,레벨,충족도를 가져옵니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "타인 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = StrangerConstant.strangerResponse,
                                    description = "타인의 정보 조회 성공했습니다"
                            )))})
    @GetMapping()
    public SuccessResponse<StrangerResponse> getStrangerProfile(@RequestParam String nickname) {
        return new SuccessResponse<>(strangerService.getStrangerProfile(nickname));
    }
    @Operation(summary = "타인이 작성한 꿀팁 목록", description = "타인이 작성한 꿀팁 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = StrangerConstant.strangerHoneyTipsResponse,
                                    description = "타유저가 작성한 꿀팁들을 불러왔습니다"
                            )))})
    @GetMapping("/honeytip/{userId}")
    public SuccessResponse<HoneyTipPaging> strangerHoneyTip(final Pageable pageable, @PathVariable final Long userId) {
        return new SuccessResponse<>(strangerService.strangerHoneyTip(pageable,userId));
    }
    @Operation(summary = "타인이 참여한 거래 목록", description = "타인이 참여한 거래 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여한 거래 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = StrangerConstant.participatedDealsResponse,
                                    description = "타인이 참여한 거래를 조회했습니다"
                            )))})
    @GetMapping("/participate-deals")
    public SuccessResponse<Message> participateDeals() {
        return new SuccessResponse<>(strangerService.participateDeals());
    }
}