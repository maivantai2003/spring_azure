package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.ChiTietBenh;
import com.nhom27.nhatkykhambenh.model.ChiTietBenh;
import com.nhom27.nhatkykhambenh.model.TongQuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IChiTietBenhRepo extends JpaRepository<ChiTietBenh, Integer> {
    List<ChiTietBenh> findByTongQuanAndTrangThaiBenhHienTai(TongQuan tongQuan, Boolean trangThaiBenh);

    ChiTietBenh findByTongQuanAndTenBenh(TongQuan tongQuan, String tenBenh);

    @Query("SELECT ctb FROM ChiTietBenh ctb " +
            "JOIN ctb.tongQuan tq " +
            "JOIN tq.nguoiDung nd " +
            "WHERE nd.giaDinh.maGiaDinh = :maGiaDinh")
    List<ChiTietBenh> findByGiaDinh(@Param("maGiaDinh") String maGiaDinh);
}
