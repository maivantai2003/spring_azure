package com.nhom27.nhatkykhambenh.security;

import com.nhom27.nhatkykhambenh.dto.NguoiDungDTO;
import com.nhom27.nhatkykhambenh.mapper.NguoiDungMapper;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.TaiKhoan;
import com.nhom27.nhatkykhambenh.service.interfaces.INguoiDungService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private INguoiDungService nguoiDungService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ADMIN"));

        if (isAdmin) {
            response.sendRedirect("/admin/dashboard");
        }
        else {
            TaiKhoan taiKhoan = (TaiKhoan) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungService.getById(taiKhoan.getMaNguoiDung());

            request.getSession().setAttribute("taikhoan", taiKhoan);
            request.getSession().setAttribute("nguoidungLogged", nguoiDung);

            response.sendRedirect("/");
        }
    }
}