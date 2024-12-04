package com.nhom27.nhatkykhambenh.dto;

import com.nhom27.nhatkykhambenh.model.TaiKhoan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomOAuth2User implements OAuth2User {
    private OAuth2User oAuth2User;
    private TaiKhoan taiKhoan;

    public CustomOAuth2User(OAuth2User oAuth2User, TaiKhoan taiKhoan) {
        this.oAuth2User = oAuth2User;
        this.taiKhoan = taiKhoan;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return taiKhoan.getDanhSachRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }
}
