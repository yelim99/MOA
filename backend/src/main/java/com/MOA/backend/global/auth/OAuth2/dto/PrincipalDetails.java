package com.MOA.backend.global.auth.OAuth2.dto;

import com.MOA.backend.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class PrincipalDetails implements OAuth2User, UserDetails {

    private final User user;
    private final Map<String, Object> attributes;
    private final String attributeKey;

    public PrincipalDetails(User user, Map<String, Object> attributes, String attributeKey, boolean exist) {
        this.user = user;
        this.attributes = attributes;
        this.attributeKey = attributeKey;
    }

    @Override
    public String getName() {
        return attributes.get(attributeKey).toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
