package com.MOA.backend.global.auth.OAuth2.service;

import com.MOA.backend.domain.user.entity.User;
import com.MOA.backend.domain.user.service.UserService;
import com.MOA.backend.global.auth.OAuth2.dto.OAuth2Attribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2Attribute에서 사용자 정보를 가져옵니다.
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Map<String, Object> userAttribute = oAuth2Attribute.converToMap();
        String email = (String) userAttribute.get("email");
        String nickname = (String) userAttribute.get("nickname");
        String picture = (String) userAttribute.get("picture");

        // 사용자 존재 여부 확인
        Optional<User> findUser = userService.findByUserEmail(email);

        // 사용자가 없으면 새로운 사용자 저장
        if (findUser.isEmpty()) {
            log.info("새 사용자 저장: 이메일 = {}", email);
            User newUser = new User();
            newUser.setUserEmail(email);
            newUser.setUserName(nickname);
            newUser.setUserImage(picture);
            userService.saveUser(newUser);  // 새로운 사용자 저장

            userAttribute.put("isNewUser", true);  // 신규 사용자 플래그 추가
        } else {
            log.info("기존 사용자 정보 불러오기: 이메일 = {}", email);
            userAttribute.put("isNewUser", false);  // 기존 사용자 플래그 추가
        }

        // 사용자 정보로 OAuth2User 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                userAttribute, "email");
    }
}


