package com.api.ttoklip.domain.Login.main.service;

import com.api.ttoklip.domain.Login.main.dto.request.LoginCondition;
import com.api.ttoklip.domain.Login.main.dto.request.LogoutCondition;
import com.api.ttoklip.domain.Login.main.dto.response.LoginResponse;
import com.api.ttoklip.domain.Login.main.dto.response.LogoutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    public LoginResponse login(LoginCondition loginCondition){
        return null;
    }
    public LoginResponse kakaoLogin(){
        return null;
    }
    public LoginResponse naverLogin(){
        return null;
    }
    public LogoutResponse logout(LogoutCondition logoutCondition){return null;}
}
