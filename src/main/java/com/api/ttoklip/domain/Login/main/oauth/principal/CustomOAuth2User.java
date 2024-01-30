package com.api.ttoklip.domain.login.main.oauth.principal;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
@Data
public class CustomOAuth2User implements OAuth2User {
    private Member user;
    private Map<String, Object> attributes;

    public CustomOAuth2User(Member user, Map<String, Object> attributes){
        this.user=user;
        this.attributes=attributes;
    }

    @Override
    public <A> A getAttribute(String name){
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER"));
    }

    @Override
    public String getName() {
        if(user.getProvider().equals("kakao")){
            return ((Map<?,?>) attributes.get("properties")).get("nickName").toString();
        }else if (user.getProvider().equals("naver")){
            return ((Map<?,?>) attributes.get("response")).get("nickName").toString();
        }

        return null;
    }
}
