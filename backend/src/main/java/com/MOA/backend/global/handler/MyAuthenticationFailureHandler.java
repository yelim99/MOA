//package com.MOA.backend.global.handler;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//@Slf4j
//public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
//            throws IOException, ServletException {
//        log.error("Authentication failed: {}", exception.getMessage());
//        exception.printStackTrace();
//        // JSON 형식으로 실패 응답
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write("{\"error\":\"Authentication failed\", \"message\":\"" + exception.getMessage() + "\"}");
//    }
//
//}
