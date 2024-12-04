package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaiKhoanRepo extends JpaRepository<TaiKhoan, Integer> {
    TaiKhoan findBySoDienThoai(String soDienThoai);

    TaiKhoan findBySoDienThoaiAndMatKhau(String soDienThoai, String matKhau);
}
