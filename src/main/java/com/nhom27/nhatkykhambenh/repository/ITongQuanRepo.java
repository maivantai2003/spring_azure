package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.TongQuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITongQuanRepo extends JpaRepository<TongQuan, Integer> {
    TongQuan findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
}
