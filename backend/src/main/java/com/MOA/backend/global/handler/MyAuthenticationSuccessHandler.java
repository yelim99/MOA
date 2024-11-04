package com.MOA.backend.global.handler;

import com.MOA.backend.domain.user.dto.UserSignupRequestDto;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.auth.jwt.service.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // 로그인한 회원 존재 여부를 가져올 때 null 체크 추가
        boolean isExist = Boolean.TRUE.equals(oAuth2User.getAttribute("exist"));



        if (isExist) {
            // accessToken을 쿼리스트링에 담는 url을 만들어준다.
            String targetUrl = "http://localhost:3000/loginSuccess";

            String token = jwtUtil.generateAccessToken(email, );
            log.info("jwtToken = {}", token);

            response.setHeader("Authorization", "Bearer" + token);

            // 로그인 확인 페이지로 리다이렉트 시킨다.
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } else {

            String nickname = oAuth2User.getAttribute("nickname");
            String picture = oAuth2User.getAttribute("picture");

            if (nickname == null) {
                nickname = "User_" + UUID.randomUUID().toString().substring(0, 8);
            }

            if (picture == null) {
                picture = "https://example.com/default-profile.png"; // 기본 이미지 URL
            }

            UserSignupRequestDto newUser = new UserSignupRequestDto(email, nickname, picture);

            userService.signup(newUser);

            String targetUrl = "http://localhost:3000/loginSuccess";

            response.setHeader("Authorization", "Bearer" + token.getAccessToken());

            // 로그인 확인 페이지로 리다이렉트 시킨다.
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }

}

