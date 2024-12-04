package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.ChiTietTiemChung;
import com.nhom27.nhatkykhambenh.model.ChiTietTiemChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IChiTietTiemChungRepo extends JpaRepository<ChiTietTiemChung, Integer> {
    Optional<ChiTietTiemChung> findByMaTiemChungAndMaNguoiDung(Integer maTiemChung, Integer maNguoiDung);

    List<ChiTietTiemChung> findAllByMaNguoiDung(Integer maNguoiDung);

    @Query("SELECT COUNT(ct) FROM ChiTietTiemChung ct WHERE ct.maTiemChung = :maTiemChung AND ct.trangThai = :trangThai")
    Integer countByMaTiemChungAndTrangThai(@Param("maTiemChung") Integer maTiemChung, @Param("trangThai") Boolean trangThai);

    @Query("SELECT cttc FROM ChiTietTiemChung cttc WHERE cttc.ngayTiem BETWEEN :fromDate AND :toDate")
    List<ChiTietTiemChung> findByDateRange(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("SELECT cttc FROM ChiTietTiemChung cttc JOIN cttc.nguoiDung nd WHERE cttc.ngayTiem BETWEEN :fromDate AND :toDate AND nd.giaDinh.maGiaDinh = :maGiaDinh")
    List<ChiTietTiemChung> findByDateRangeAndGiaDinh(@Param("fromDate") LocalDateTime fromDate,
                                             @Param("toDate") LocalDateTime toDate,
                                             @Param("maGiaDinh") String maGiaDinh);

    @Query("SELECT cttc FROM ChiTietTiemChung cttc JOIN cttc.nguoiDung nd WHERE nd.giaDinh.maGiaDinh = :maGiaDinh")
    List<ChiTietTiemChung> findByGiaDinh(@Param("maGiaDinh") String maGiaDinh);
}
