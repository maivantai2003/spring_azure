package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.DonThuoc;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDonThuocRepo extends JpaRepository<DonThuoc, Integer> {
//    XetNghiem findByChiTietKhamBenh_MaChiTietKhamBenh(Integer maChiTietKhamBenh);
}
