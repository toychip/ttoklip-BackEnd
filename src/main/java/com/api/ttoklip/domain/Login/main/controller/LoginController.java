package com.api.ttoklip.domain.Login.main.controller;

import com.api.ttoklip.domain.Login.main.constant.LoginResponseConstant;
import com.api.ttoklip.domain.Login.main.dto.request.LoginCondition;
import com.api.ttoklip.domain.Login.main.dto.request.LogoutCondition;
import com.api.ttoklip.domain.Login.main.dto.response.LoginResponse;
import com.api.ttoklip.domain.Login.main.dto.response.LogoutResponse;
import com.api.ttoklip.domain.Login.main.service.LoginService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Login", description = "Login API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "로그인 API",
            description = "사용자의 인증을 시도하여 토큰을 생성하여 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = LoginResponseConstant.successResponse,
                                    description = "로그인 성공 시 응답"
                            )))})
    @PostMapping("/login")
    public SuccessResponse<LoginResponse> login(@RequestBody LoginCondition loginCondition) {
        return new SuccessResponse<>(loginService.login(loginCondition));
    }
    @Operation(summary = "로그아웃 API",
            description = "사용자의 로그아웃을 처리합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = LoginResponseConstant.logoutSuccessResponse,
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
                                    value = LoginResponseConstant.kakaoSuccessResponse,
                                    description = "로그인 성공 시 응답"
                            )))})
    @PostMapping("/kakaologin")
    public SuccessResponse<LoginResponse> kakaoLogin() {
        return new SuccessResponse<>(loginService.kakaoLogin());
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
                                    value = LoginResponseConstant.naverSuccessResponse,
                                    description = "로그인 성공 시 응답"
                            )))})
    @PostMapping("/naverlogin")
    public SuccessResponse<LoginResponse> naverLogin() {
        return new SuccessResponse<>(loginService.naverLogin());
    }
}