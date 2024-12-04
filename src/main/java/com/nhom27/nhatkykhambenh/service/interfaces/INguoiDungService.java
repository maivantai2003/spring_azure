package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.model.GiaDinh;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.TaiKhoan;

import java.util.List;

public interface INguoiDungService {
    List<NguoiDung> getAllNguoiDung();

    List<NguoiDung> getDsNguoiDungByGiaDinh(GiaDinh giaDinh);

    void saveNguoiDung(NguoiDung nguoiDung, TaiKhoan taiKhoan);

    void deleteById(Integer id);

    NguoiDung getById(Integer id);

    NguoiDung findByEmail(String email);
}
