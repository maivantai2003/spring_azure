package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.dto.CustomOAuth2User;
import com.nhom27.nhatkykhambenh.enums.MoiQuanHe;
import com.nhom27.nhatkykhambenh.model.*;
import com.nhom27.nhatkykhambenh.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private ITaiKhoanRepo taiKhoanRepo;

    @Autowired
    private INguoiDungRepo nguoiDungRepo;

    @Autowired
    private IGiaDinhRepo giaDinhRepo;

    @Autowired
    private ITongQuanRepo tongQuanRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleRepo roleRepo;

    @Autowired
    private HttpServletRequest request;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String provider = userRequest.getClientRegistration().getRegistrationId(); // Xác định Google/Facebook

        String providerId;
        if (provider.equalsIgnoreCase("facebook")) {
            providerId = oAuth2User.getAttribute("id");
        }
        else if (provider.equalsIgnoreCase("google")) {
            providerId = oAuth2User.getAttribute("sub");
        }
        else {
            throw new RuntimeException("Unsupported provider: " + provider);
        }

        if (providerId == null || providerId.isEmpty()) {
            throw new RuntimeException("Provider unsupported: " + provider);
        }

        NguoiDung existNguoiDung  = nguoiDungRepo.findByEmail(email);

        if(existNguoiDung == null){
            GiaDinh giaDinh = new GiaDinh();
            giaDinh.setSoLuong(1);
            giaDinhRepo.saveAndFlush(giaDinh);

            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setEmail(email);
            nguoiDung.setTenNguoiDung(name);
            nguoiDung.setMoiQuanHe(MoiQuanHe.TOI);
            nguoiDung.setGiaDinh(giaDinh);
            nguoiDungRepo.save(nguoiDung);

            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setMaNguoiDung(nguoiDung.getMaNguoiDung());
            taiKhoan.setSoDienThoai(email);
            taiKhoan.setMatKhau(passwordEncoder.encode(providerId));
            taiKhoan.setGiaDinh(giaDinh);
            taiKhoan.setNguoiDung(nguoiDung);

            Role defaultRole = roleRepo.findByName("USER");
            taiKhoan.setDanhSachRole(List.of(defaultRole));
            taiKhoanRepo.saveAndFlush(taiKhoan);

            TongQuan tongQuan = new TongQuan();
            tongQuan.setNguoiDung(nguoiDung);
            tongQuanRepo.save(tongQuan);

            existNguoiDung = nguoiDung;
        }

        NguoiDung finalExistNguoiDung = existNguoiDung;

        TaiKhoan taiKhoan = taiKhoanRepo.findById(existNguoiDung.getMaNguoiDung())
                .orElseThrow(() -> new RuntimeException("Tai khoan not found for user id: " + finalExistNguoiDung.getMaNguoiDung()));

        CustomOAuth2User customUser = new CustomOAuth2User(oAuth2User, taiKhoan);

        request.getSession().setAttribute("taikhoan", taiKhoan);
        request.getSession().setAttribute("nguoidungLogged", finalExistNguoiDung);

        return customUser;
    }
}
