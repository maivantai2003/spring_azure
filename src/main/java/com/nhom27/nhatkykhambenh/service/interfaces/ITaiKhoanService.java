package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.dto.RegistrationDTO;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.Role;
import com.nhom27.nhatkykhambenh.model.TaiKhoan;

public interface ITaiKhoanService {
    void saveTaiKhoan(TaiKhoan taiKhoan);

    void deleteById(Integer id);

    NguoiDung registerUser(RegistrationDTO registrationDTO, Role role);

    TaiKhoan findBySoDienThoaiAndMatKhau(String soDienThoai, String matKhau);

    TaiKhoan findBySoDienThoai(String soDienThoai);

    TaiKhoan findById(Integer maNguoiDung);

    TaiKhoan getCurrentUser();
}