package com.api.ttoklip.domain.auth.converter;

import com.api.ttoklip.domain.auth.dto.request.SignUpReq;
import com.api.ttoklip.domain.user.domain.Role;
import com.api.ttoklip.domain.user.domain.User;

public class AuthConverter {

    public static User toUser(SignUpReq signUpReq, String imageUrl) {
        return User.builder()
                .providerId(signUpReq.getProviderId())
                .name(signUpReq.getName())
                .nickname(signUpReq.getNickname())
                .imageUrl(imageUrl)
                .role(Role.USER)
                .build();
    }
}
