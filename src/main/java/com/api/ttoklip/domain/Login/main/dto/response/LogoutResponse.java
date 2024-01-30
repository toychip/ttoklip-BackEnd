package com.api.ttoklip.domain.Login.main.dto.response;

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
