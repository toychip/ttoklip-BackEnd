package com.api.stranger.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.stranger.application.StrangerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Stranger", description = "타인의 프로필 조회 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stranger")
public class StrangerController {
    private final static int PAGE_SIZE = 10;
    private final StrangerFacade strangerService;

    @Operation(summary = "타인 정보 불러오기", description = "타인의 기본 정보인 닉네임,동네,레벨,충족도를 가져옵니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "타인 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = StrangerConstant.strangerResponse,
                                    description = "타인의 정보 조회 성공했습니다"
                            )))})
    @GetMapping()
    public TtoklipResponse<StrangerResponse> getStrangerProfile(@RequestParam String nickname) {
        return new TtoklipResponse<>(strangerService.getStrangerProfile(nickname));
    }

    @Operation(summary = "타인이 작성한 꿀팁 목록", description = "타인이 작성한 꿀팁 글 목록 불러오기")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "작성한 글 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = StrangerConstant.strangerHoneyTipsResponse,
                                    description = "타유저가 작성한 꿀팁들을 불러왔습니다"
                            )))})
    @GetMapping("/honeytip/{userId}")
    public TtoklipResponse<HoneyTipPaging> strangerHoneyTip(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page,
            @PathVariable final Long userId) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new TtoklipResponse<>(strangerService.strangerHoneyTip(pageable, userId));
    }

//    @Operation(summary = "타인이 참여한 거래 목록", description = "타인이 참여한 거래 목록 불러오기")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "참여한 거래 목록 조회 성공",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = SuccessResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = StrangerConstant.participatedDealsResponse,
//                                    description = "타인이 참여한 거래를 조회했습니다"
//                            )))})
//    @GetMapping("/participate-deals/{userId}")
//    public SuccessResponse<Message> participateDeals(
//            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
//            @RequestParam(required = false, defaultValue = "0") final int page,
//            @PathVariable final Long userId) {
//        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
//        return new SuccessResponse<>(strangerService.participateDeals(pageable, userId));
//    }
}
