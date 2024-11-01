//package com.MOA.backend.global.auth.OAuth2.service;
//
//import com.MOA.backend.domain.user.entity.User;
//import com.MOA.backend.domain.user.service.UserService;
//import com.MOA.backend.global.auth.OAuth2.dto.OAuth2Attribute;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//
//    private final UserService userService;
//
//    @Transactional
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        // 기본 OAuth2UserService 객체로부터 OAuth2User 정보를 불러옴
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
//
//        //  위의 서비스에서 정보 불러오기
//        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
//
//        // 클라이언트 ID (예: google, naver, kakao)와 사용자 속성 이름을 가져옴
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//
//        String userNameAttributeName = userRequest.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        // OAuth2Attribute를 사용해 사용자 정보 추상화
//        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//        Map<String, Object> attributes = oAuth2Attribute.convertToMap();
//
//        // 사용자 이메일로 등록된 사용자 검색
//        String email = (String) attributes.get("email");
//        Optional<User> optionalUser = userService.findByUserEmail(email);
//
//        if (optionalUser.isEmpty()) {
//            attributes.put("exist", false);
//
//            return new DefaultOAuth2User(
//                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                    attributes, "email");
//        }
//
//        attributes.put("exist", true);
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                attributes, "email");
//
//    }
//}
