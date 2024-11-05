package com.MOA.backend.global.handler;

import com.MOA.backend.domain.user.dto.UserSignupRequestDto;
import com.MOA.backend.domain.user.entity.User;
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
import java.util.Optional;
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

        String token;


        if (isExist) {
            Optional<User> existingUser = userService.findByUserEmail(email);
            token = jwtUtil.generateAccessToken(existingUser.get().getUserId());
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

            User createdUser = userService.signup(newUser);

            token = jwtUtil.generateAccessToken(createdUser.getUserId());
        }

        response.setHeader("AuthorizationJWT", "Bearer " + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\":\"" + token + "\"}");

    }

}

