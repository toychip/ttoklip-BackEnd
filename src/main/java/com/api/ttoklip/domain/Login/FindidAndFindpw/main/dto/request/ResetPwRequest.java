package com.api.ttoklip.domain.Login.FindidAndFindpw.main.dto.request;
import lombok.Getter;

@Getter
public class ResetPwRequest {
    private String userEmail;
    private String newPassword;
}
