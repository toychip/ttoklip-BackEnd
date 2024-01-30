package com.api.ttoklip.domain.login.find.main.dto.request;
import lombok.Getter;

@Getter
public class ResetPwRequest {
    private String userEmail;
    private String newPassword;
}
