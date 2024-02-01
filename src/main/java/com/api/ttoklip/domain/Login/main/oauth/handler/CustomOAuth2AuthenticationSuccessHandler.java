package com.api.ttoklip.domain.login.main.oauth.handler;

import com.api.ttoklip.domain.login.main.main.dto.response.LoginResponse;
import com.api.ttoklip.domain.login.main.oauth.principal.CustomOAuth2User;
import com.api.ttoklip.domain.login.main.oauth.userinfo.KakaoUserInfo;
import com.api.ttoklip.domain.login.main.oauth.userinfo.NaverUserInfo;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.global.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

}
