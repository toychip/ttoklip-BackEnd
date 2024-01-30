package com.api.ttoklip.domain.login.signup.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignUpResponse {
    private String message;
    private long userId;
}