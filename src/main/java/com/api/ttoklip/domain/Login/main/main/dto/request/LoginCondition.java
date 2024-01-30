package com.api.ttoklip.domain.login.main.main.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginCondition {
    @Schema(description = "사용자 아이디")
    private String userAccount;
    @Schema(description = "사용자 비밀번호")
    private String userPassword;
}
