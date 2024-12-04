package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.ChiTietDonThuoc;
import com.nhom27.nhatkykhambenh.model.ChiTietTiemChung;
import com.nhom27.nhatkykhambenh.model.DonThuoc;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IChiTietDonThuocRepo extends JpaRepository<ChiTietDonThuoc, ChiTietDonThuoc.ChiTietDonThuocId> {
    List<ChiTietDonThuoc> findByMaChiTietKhamBenh(Integer maChiTietKhamBenh);

    Optional<ChiTietDonThuoc> findByMaDonThuocAndMaChiTietKhamBenh(Integer maDonThuoc, Integer maChiTietKhamBenh);

    List<ChiTietDonThuoc> findByMaDonThuocIn (List<Integer> maDonThuoc);
}
