package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.dto.ChiTietDonThuocDTO;
import com.nhom27.nhatkykhambenh.model.ChiTietDonThuoc;
import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import com.nhom27.nhatkykhambenh.model.DonThuoc;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IChiTietDonThuocService {

    void saveChiTietDonThuoc(ChiTietDonThuoc chiTietDonThuoc, Integer maDonThuoc, Integer maChiTietKhamBenh);

    ChiTietDonThuoc findById(Integer maDonThuoc, Integer maChiTietKhamBenh);

    void deleteById(Integer maDonThuoc, Integer maChiTietKhamBenh);

    void deleteAllByIds(Integer maChiTietKhamBenh, List<Integer> maDonThuocs);

    Page<ChiTietDonThuoc> getDSChiTietDonThuoc(Pageable pageable, String query, Integer maDonThuoc);

}
