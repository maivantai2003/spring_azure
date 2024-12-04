package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.KhamBenh;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IXetNghiemRepo extends JpaRepository<XetNghiem, Integer> {
    XetNghiem findByChiTietKhamBenh_MaChiTietKhamBenh(Integer maChiTietKhamBenh);

    @Query("SELECT xn FROM XetNghiem xn WHERE xn.maChiTietKhamBenh = :maChiTietKhamBenh")
    List<XetNghiem> findByMaChiTietKhamBenh(@Param("maChiTietKhamBenh") Integer maChiTietKhamBenh);

    @Query("SELECT xn FROM XetNghiem xn JOIN xn.chiTietKhamBenh ctkb WHERE ctkb.thoiGianVaoKham BETWEEN :fromDate AND :toDate")
    List<XetNghiem> findByDateRange(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("SELECT xn FROM XetNghiem xn " +
            "JOIN xn.chiTietKhamBenh ctkb " +
            "JOIN ctkb.khamBenh kb " +
            "JOIN kb.nguoiDung nd " +
            "WHERE ctkb.thoiGianVaoKham BETWEEN :fromDate AND :toDate AND nd.giaDinh.maGiaDinh = :maGiaDinh")
    List<XetNghiem> findByDateRangeAndGiaDinh(@Param("fromDate") LocalDateTime fromDate,
                                             @Param("toDate") LocalDateTime toDate,
                                             @Param("maGiaDinh") String maGiaDinh);

    @Query("SELECT xn FROM XetNghiem xn " +
            "JOIN xn.chiTietKhamBenh ctkb " +
            "JOIN ctkb.khamBenh kb " +
            "JOIN kb.nguoiDung nd " +
            "WHERE nd.giaDinh.maGiaDinh = :maGiaDinh")
    List<XetNghiem> findByGiaDinh(@Param("maGiaDinh") String maGiaDinh);
}
