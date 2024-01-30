package com.api.ttoklip.domain.Login.main.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LogoutCondition {
    @Schema(description = "사용자 인증 토큰")
    private String userToken;
}
