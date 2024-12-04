package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import com.nhom27.nhatkykhambenh.model.HinhAnh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IHinhAnhRepo extends JpaRepository<HinhAnh, Long> {
    List<HinhAnh> getHinhAnhsByChiTietKhamBenh(ChiTietKhamBenh chiTietKhamBenh);
}
