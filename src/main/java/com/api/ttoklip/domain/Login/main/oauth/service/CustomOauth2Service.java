package com.api.ttoklip.domain.login.main.oauth.service;

import com.api.ttoklip.domain.login.main.oauth.principal.CustomOAuth2User;
import com.api.ttoklip.domain.login.main.oauth.userinfo.OAuth2UserInfo;
import com.api.ttoklip.domain.login.main.oauth.userinfo.OAuthUserInfoFactory;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOauth2Service extends DefaultOAuth2UserService {


    private final MemberRepository memberRepository;

    private @Lazy PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request)throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(request);
        log.info("----------getAttributes : {}", oAuth2User.getAttributes());

        String provider = request.getClientRegistration().getRegistrationId();

        //어떤 소셜로그인인지 구분
        OAuth2UserInfo oAuth2UserInfo = OAuthUserInfoFactory.getOAuthUserInfo(provider,oAuth2User.getAttributes());
        //회원가입 유무 확인
        Optional<Member> member=memberRepository.findMemberByUserEmailAndProvider(oAuth2UserInfo.getEmail(),provider);

        if(member.isEmpty()){
            Member newMember=Member.builder()
                    .userNickname(oAuth2UserInfo.getName())
                    .userEmail(oAuth2UserInfo.getEmail())
                    .provider(provider)
                    .password(passwordEncoder.encode("oauth"))
                    .role(Role.GENERAL)
                    .build();

            memberRepository.save(newMember);//변경된 일반 유저 저장

            return new CustomOAuth2User(newMember, oAuth2User.getAttributes());
        }
        return new CustomOAuth2User(member.get(),oAuth2User.getAttributes());
    }
}
