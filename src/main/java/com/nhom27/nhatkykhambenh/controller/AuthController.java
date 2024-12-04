package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.*;
import com.nhom27.nhatkykhambenh.model.PasswordResetToken;
import com.nhom27.nhatkykhambenh.model.Role;
import com.nhom27.nhatkykhambenh.repository.ITokenRepository;
import com.nhom27.nhatkykhambenh.security.JwtUtil;
import com.nhom27.nhatkykhambenh.service.implementation.TaiKhoanService;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.TaiKhoan;
import com.nhom27.nhatkykhambenh.service.interfaces.IEmailService;
import com.nhom27.nhatkykhambenh.service.interfaces.INguoiDungService;
import com.nhom27.nhatkykhambenh.service.interfaces.IRoleService;
import com.nhom27.nhatkykhambenh.service.interfaces.ITaiKhoanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    @Autowired
    private INguoiDungService nguoiDungService;

    @Autowired
    private ITaiKhoanService taiKhoanService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private ITokenRepository tokenRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("nguoidung");
        session.removeAttribute("taikhoan");
        session.removeAttribute("nguoidungLogged");
        return "redirect:/login?logout";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setNguoidung(new NguoiDungDTO());
        registrationDTO.setTaikhoan(new TaiKhoanDTO());

        model.addAttribute("registration", registrationDTO);
        return "register";
    }

    @PostMapping("/register")
    public String saveTaiKhoan(@ModelAttribute("registration") RegistrationDTO registrationDTO, Model model) {
        try {
            if (!registrationDTO.getTaikhoan().getMatKhau().equals(registrationDTO.getRePassword())) {
                throw new IllegalArgumentException("Mật khẩu nhập lại không khớp!");
            }

            Role role = roleService.findByRoleName("USER");

            NguoiDung nguoiDung = taiKhoanService.registerUser(registrationDTO, role);

            List<String> roles = Collections.singletonList(role.getName());

            String jwtToken = jwtUtil.generateToken(nguoiDung.getSoDienThoai(), roles);

            return "redirect:/login?registerSuccess=true&jwt=" + jwtToken;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/users/thongtinkhac")
    public String getThongTinKhac(Model model,HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoidung");
        model.addAttribute("nguoiDung", nguoiDung);
        return "users/thongtinkhac";
    }

    @GetMapping("/forgotpassword")
    public String forgotPassword() {
        return "users/forgotpassword";
    }

    @PostMapping("/forgotpassword")
    public String forgotPassword(@ModelAttribute NguoiDungDTO nguoiDungDTO) {
        try {
            String output = "";
            NguoiDung nguoiDung = nguoiDungService.findByEmail(nguoiDungDTO.getEmail());
            if (nguoiDung != null) {
                output = emailService.sendEmail(nguoiDung);
            }

            if (output.equals("success"))
                return "redirect:/forgotpassword?success&email=" + nguoiDungDTO.getEmail();
            else {
                String error = "Email này không tồn tại trong hệ thống";
                return "redirect:/login?error=" + error;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login?error=InternalError";
        }
    }

    @GetMapping("/resetpassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken reset = tokenRepository.findByToken(token);
        if (reset != null && emailService.hasExpired(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getNguoiDung().getEmail());
            return "users/resetpassword";
        }
        return "redirect:/forgotpassword?error";
    }

    @PostMapping("/resetpassword")
    public String passwordResetProcess(@ModelAttribute ResetPasswordDTO resetPasswordDTO) {
        NguoiDung nguoiDung = nguoiDungService.findByEmail(resetPasswordDTO.getEmail());
        TaiKhoan taiKhoan = taiKhoanService.findById(nguoiDung.getMaNguoiDung());

        if (taiKhoan != null) {
            taiKhoan.setMatKhau(resetPasswordDTO.getPassword());
            taiKhoanService.saveTaiKhoan(taiKhoan);
        }

        String success = "Đổi mật khẩu thành công";
        return "redirect:/login?success=" + success;
    }
}