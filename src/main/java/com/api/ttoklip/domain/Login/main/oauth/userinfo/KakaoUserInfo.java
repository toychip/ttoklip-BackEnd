package com.api.ttoklip.domain.login.main.oauth.userinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;


@Data
@AllArgsConstructor
@ToString
@Builder
public class KakaoUserInfo implements OAuth2UserInfo{
    private Map<String, Object> attributes;

    @Override
    public String getProfile(){
        Map<String,Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String,Object> kakaoProfile=(Map<String, Object>) kakaoAccount.get("profile");

        return (String) kakaoProfile.get("profile_image_url");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("kakao_account")).get("email");
    }

    @Override
    public String getName() {
        Map<String,Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String,Object> kakaoProfile=(Map<String, Object>) kakaoAccount.get("profile");

        return (String) kakaoProfile.get("nickName");
    }

    @Override
    public String getPassword() {
        return "kakao";
    }

}
