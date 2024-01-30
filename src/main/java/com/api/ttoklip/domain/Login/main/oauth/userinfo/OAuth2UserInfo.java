package com.api.ttoklip.domain.login.main.oauth.userinfo;

public interface OAuth2UserInfo {
    String getProfile();
    String getEmail();
    String getName();
    String getPassword();
}
