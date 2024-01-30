package com.api.ttoklip.domain.login.main.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LogoutResponse {
    private String userId;
    private String userName;
    private String message;
}
