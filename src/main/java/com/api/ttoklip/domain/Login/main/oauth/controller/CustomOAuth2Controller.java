package com.api.ttoklip.domain.login.main.oauth.controller;

import com.api.ttoklip.domain.login.main.main.dto.request.LogoutCondition;
import com.api.ttoklip.domain.login.main.main.dto.response.LoginResponse;
import com.api.ttoklip.domain.login.main.main.dto.response.LogoutResponse;
import com.api.ttoklip.domain.login.main.main.service.LoginService;
import com.api.ttoklip.domain.login.main.oauth.constant.CustomOAuth2Constant;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "OAuth Login", description = "OAuth Login API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/oauth")
public class CustomOAuth2Controller {
    private LoginService loginService;
    @Operation(summary = "로그아웃 API",
            description = "사용자의 로그아웃을 처리합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CustomOAuth2Constant.logoutSuccessResponse,
                                    description = "로그아웃 성공 시 응답"
                            )))})
    @PostMapping("/logout")
    public SuccessResponse<LogoutResponse> login(@RequestBody LogoutCondition logoutCondition) {
        return new SuccessResponse<>(loginService.logout(logoutCondition));
    }

    @Operation(summary = "카카오 로그인 API",
            description = "카카오톡 로그인을 실행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CustomOAuth2Constant.kakaoSuccessResponse,
                                    description = "로그인 성공 시 응답"
                            )))})
    @PostMapping("/kakaologin")
    public SuccessResponse<LoginResponse> kakaoLogin() {
        return null;//후에 수정 예정

    }

    @Operation(summary = "네이버 로그인 API",
            description = "네이버 로그인을 실행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CustomOAuth2Constant.naverSuccessResponse,
                                    description = "로그인 성공 시 응답"
                            )))})
    @PostMapping("/naverlogin")
    public SuccessResponse<LoginResponse> naverLogin() {
        return null;//후에 수정 예정
    }
}
