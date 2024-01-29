package com.api.ttoklip.domain.Login.FindidAndFindpw.main.service;

import com.api.ttoklip.domain.Login.FindidAndFindpw.main.dto.request.FindIdRequest;
import com.api.ttoklip.domain.Login.FindidAndFindpw.main.dto.response.FindIdResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindIdService {
    public FindIdResponse findAuthByEmail(FindIdRequest findIdRequest){//추가 구현 필요
        return null;
    }
    public Long requestEmailVerification(String email){//추가 구현 필요
        return null;
    }
    public String verifyEmail(Long emailVerifyNum){//추가 구현 필요
        return null;
    }//
}
