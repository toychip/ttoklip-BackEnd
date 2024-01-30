package com.api.ttoklip.domain.login.main.oauth.service;

import com.api.ttoklip.domain.login.main.oauth.userinfo.OAuth2UserInfo;
import com.api.ttoklip.domain.login.main.oauth.userinfo.OAuthUserInfoFactory;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOauth2Service extends DefaultOAuth2UserService {


    private final MemberRepository memberRepository;

    public OAuth2User loadUser(OAuth2UserRequest request)throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(request);
        log.info("----------getAttributes : {}", oAuth2User.getAttributes());

        String provider = request.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = OAuthUserInfoFactory.getOAuthUserInfo(provider,oAuth2User.getAttributes());
        //회원가입 유무 확인
        //Optional<Member> member=memberRepository.find
        /*if(member.isEmpty()){
            Member newMember=Member.builder()
        }*/
        return null;
    }
}
