package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.dto.CustomOAuth2User;
import com.nhom27.nhatkykhambenh.dto.NguoiDungDTO;
import com.nhom27.nhatkykhambenh.dto.RegistrationDTO;
import com.nhom27.nhatkykhambenh.dto.TaiKhoanDTO;
import com.nhom27.nhatkykhambenh.enums.MoiQuanHe;
import com.nhom27.nhatkykhambenh.model.*;
import com.nhom27.nhatkykhambenh.repository.IGiaDinhRepo;
import com.nhom27.nhatkykhambenh.repository.INguoiDungRepo;
import com.nhom27.nhatkykhambenh.repository.ITaiKhoanRepo;
import com.nhom27.nhatkykhambenh.repository.ITongQuanRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.ITaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaiKhoanService implements ITaiKhoanService {

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

    @Override
    public void saveTaiKhoan(TaiKhoan taiKhoan) {
        try{
            taiKhoanRepo.save(taiKhoan);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public NguoiDung registerUser(RegistrationDTO registrationDTO, Role role) {
        TaiKhoanDTO taiKhoanDTO = registrationDTO.getTaikhoan();
        NguoiDungDTO nguoiDungDTO = registrationDTO.getNguoidung();

        if (taiKhoanRepo.findBySoDienThoai(taiKhoanDTO.getSoDienThoai()) != null) {
            throw new RuntimeException("Tài khoản đã tồn tại!");
        }

        GiaDinh giaDinh = new GiaDinh();
        giaDinhRepo.save(giaDinh);

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setSoDienThoai(taiKhoanDTO.getSoDienThoai());
        nguoiDung.setTenNguoiDung(nguoiDungDTO.getTenNguoiDung());
        nguoiDung.setEmail(nguoiDungDTO.getEmail());
        nguoiDung.setGiaDinh(giaDinh);
        nguoiDung.setMoiQuanHe(MoiQuanHe.TOI);
        nguoiDungRepo.save(nguoiDung);

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setMaNguoiDung(nguoiDung.getMaNguoiDung());
        taiKhoan.setSoDienThoai(taiKhoanDTO.getSoDienThoai());
        taiKhoan.setMatKhau(passwordEncoder.encode(taiKhoanDTO.getMatKhau()));
        taiKhoan.setGiaDinh(giaDinh);
        taiKhoan.setNguoiDung(nguoiDung);
        taiKhoan.setDanhSachRole(List.of(role));
        taiKhoanRepo.saveAndFlush(taiKhoan);

        TongQuan tongQuan = new TongQuan();
        tongQuan.setNguoiDung(nguoiDung);
        tongQuanRepo.save(tongQuan);

        return nguoiDung;
    }

    @Override
    public TaiKhoan findBySoDienThoaiAndMatKhau(String soDienThoai, String matKhau) {
        for (TaiKhoan i : taiKhoanRepo.findAll()) {
            if (i.getMatKhau() != null && i.getMatKhau().equals(matKhau) && i.getSoDienThoai().equals(soDienThoai)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public TaiKhoan findBySoDienThoai(String soDienThoai) {
        return taiKhoanRepo.findBySoDienThoai(soDienThoai);
    }

    @Override
    public TaiKhoan findById(Integer maNguoiDung) {
        return taiKhoanRepo.findById(maNguoiDung).get();
    }

    @Override
    public TaiKhoan getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof TaiKhoan) {
            return (TaiKhoan) principal;
        }
        else if (principal instanceof DefaultOAuth2User) {
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) principal;

            String email = oAuth2User.getAttribute("email");
            if (email == null) {
                throw new RuntimeException("Email attribute is missing in OAuth2User");
            }

            NguoiDung nguoiDung = nguoiDungRepo.findByEmail(email);
            if (nguoiDung == null) {
                throw new RuntimeException("No user found with email: " + email);
            }

            return taiKhoanRepo.findById(nguoiDung.getMaNguoiDung())
                    .orElseThrow(() -> new RuntimeException("User not found with maNguoiDung: " + nguoiDung.getMaNguoiDung()));
        }
        else if (principal instanceof CustomOAuth2User) {
            CustomOAuth2User customOAuth2User = (CustomOAuth2User) principal;

            String email = customOAuth2User.getAttribute("email");
            if (email == null) {
                throw new RuntimeException("Email attribute is missing in CustomOAuth2User");
            }

            NguoiDung nguoiDung = nguoiDungRepo.findByEmail(email);
            if (nguoiDung == null) {
                throw new RuntimeException("No user found with email: " + email);
            }

            return taiKhoanRepo.findById(nguoiDung.getMaNguoiDung())
                    .orElseThrow(() -> new RuntimeException("User not found with maNguoiDung: " + nguoiDung.getMaNguoiDung()));
        }
        else {
            throw new RuntimeException("User type not supported: " + principal.getClass().getName());
        }
    }
}