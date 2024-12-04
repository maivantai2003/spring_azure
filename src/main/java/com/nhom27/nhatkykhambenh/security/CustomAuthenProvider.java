package com.nhom27.nhatkykhambenh.security;

import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.Role;
import com.nhom27.nhatkykhambenh.model.TaiKhoan;
import com.nhom27.nhatkykhambenh.service.interfaces.INguoiDungService;
import com.nhom27.nhatkykhambenh.service.interfaces.ITaiKhoanService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
public class CustomAuthenProvider implements AuthenticationProvider {

    @Autowired
    private ITaiKhoanService taiKhoanService;

    @Autowired
    private INguoiDungService nguoiDungService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            String soDienThoai = authentication.getName();
            String matKhau = authentication.getCredentials().toString();

            TaiKhoan taiKhoan = taiKhoanService.findBySoDienThoai(soDienThoai);

            if (taiKhoan == null || !passwordEncoder.matches(matKhau, taiKhoan.getMatKhau())) {
                throw new BadCredentialsException("Số điện thoại hoặc mật khẩu không đúng!");
            }

            List<String> roles = taiKhoan.getDanhSachRole().stream()
                    .map(Role::getName)
                    .toList();

            String jwtToken = jwtUtil.generateToken(soDienThoai, roles);

            List<GrantedAuthority> authorities = taiKhoan.getDanhSachRole()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(taiKhoan, jwtToken, authorities);
        }
        else if(authentication instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication;

            String email = oAuth2User.getAttribute("email");
            NguoiDung nguoiDung = nguoiDungService.findByEmail(email);
            TaiKhoan taiKhoan = taiKhoanService.findById(nguoiDung.getMaNguoiDung());

            if (taiKhoan == null) {
                throw new BadCredentialsException("Tài khoản không tồn tại!");
            }

            List<GrantedAuthority> authorities = taiKhoan.getDanhSachRole()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            List<String> roles = taiKhoan.getDanhSachRole().stream()
                    .map(Role::getName)
                    .toList();

            String jwtToken = jwtUtil.generateToken(email, roles);

            return new UsernamePasswordAuthenticationToken(taiKhoan, jwtToken, authorities);
        }

        throw new OAuth2AuthenticationException("Authentication method not supported");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)
                || OAuth2User.class.isAssignableFrom(authentication);
    }
}

