//package com.MOA.backend.global.handler;
//
//import com.MOA.backend.global.auth.jwt.dto.GeneratedToken;
//import com.MOA.backend.global.auth.jwt.service.JwtUtil;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private final JwtUtil jwtUtil;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        String email = oAuth2User.getAttribute("email");
//
//        // 로그인한 회원 존재 여부를 가져올 때 null 체크 추가
//        boolean isExist = Boolean.TRUE.equals(oAuth2User.getAttribute("exist"));
//
//        // 이건 실제 서비스용
////        if (isExist) {
////            // 회원이 존재하면 jwt token 발행을 시작한다.
////            GeneratedToken token = jwtUtil.generateToken(email);
////            log.info("jwtToken = {}", token.getAccessToken());
////
////            // accessToken을 쿼리스트링에 담는 url을 만들어준다.
////            String targetUrl = UriComponentsBuilder.fromUriString("http://3.39.72.204/loginSuccess")
////                    .queryParam("accessToken", token.getAccessToken())
////                    .build()
////                    .encode(StandardCharsets.UTF_8)
////                    .toUriString();
////            log.info("redirect 준비");
////            // 로그인 확인 페이지로 리다이렉트 시킨다.
////            getRedirectStrategy().sendRedirect(request, response, targetUrl);
////        } else {
//
////                String provider = oAuth2User.getAttribute("provider");
////                String nickname = oAuth2User.getAttribute("nickname");
////                String picture = oAuth2User.getAttribute("picture");
//
////            String targetUrl = UriComponentsBuilder.fromUriString("http://3.39.72.204/loginSuccess")
////                    .queryParam("email", email)
////                    .queryParam("provider", provider)
////                     .queryParam("nickname", nickname)
////                      .queryParam("picture", picture)
////                    .build()
////                    .encode(StandardCharsets.UTF_8)
////                    .toUriString();
////            // 회원가입 페이지로 리다이렉트 시킨다.
////            getRedirectStrategy().sendRedirect(request, response, targetUrl);
////        }
//
//        // 테스트 코드
//        if (isExist) {
//            // 회원이 존재하면 JWT 토큰 발행
//            GeneratedToken token = jwtUtil.generateToken(email);
//            log.info("jwtToken = {}", token.getAccessToken());
//
//            // JSON 응답으로 JWT 토큰을 전달
//            response.getWriter().write("{\"accessToken\": \"" + token.getAccessToken() + "\"}");
//        } else {
//            String provider = oAuth2User.getAttribute("provider");
//            String nickname = oAuth2User.getAttribute("nickname");
//            String picture = oAuth2User.getAttribute("picture");
//
//
//            // 신규 회원인 경우, email과 provider 정보를 JSON 응답으로 전달
//            response.setContentType("application/json;charset=UTF-8");
//            response.getWriter().write("{\"email\": \"" + email + "\", " +
//                    "\"provider\": \"" + provider + "\", " +
//                    "\"nickname\": \"" + nickname + "\", " +
//                    "\"picture\": \"" + picture + "\", " +
//                    "\"message\": \"회원가입 필요\"}");
//        }
//    }
//
//}
