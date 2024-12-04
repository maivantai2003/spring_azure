package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.KhamBenh;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IKhamBenhRepo extends JpaRepository<KhamBenh, Integer> {
    List<KhamBenh> findAllByNguoiDung(NguoiDung nguoiDung);

    @Query("SELECT kb FROM KhamBenh kb JOIN kb.danhSachChiTietKhamBenh ctkb WHERE ctkb.maChiTietKhamBenh = :maChiTietKhamBenh")
    KhamBenh findByChiTietKhamBenh(@Param("maChiTietKhamBenh") Integer maChiTietKhamBenh);

    @Query("SELECT kb FROM KhamBenh kb WHERE kb.ngayKham BETWEEN :fromDate AND :toDate")
    List<KhamBenh> findByDateRange(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("SELECT kb FROM KhamBenh kb JOIN kb.nguoiDung nd WHERE kb.ngayKham BETWEEN :fromDate AND :toDate AND nd.giaDinh.maGiaDinh = :maGiaDinh")
    List<KhamBenh> findByDateRangeAndGiaDinh(@Param("fromDate") LocalDateTime fromDate,
                                              @Param("toDate") LocalDateTime toDate,
                                              @Param("maGiaDinh") String maGiaDinh);

    @Query("SELECT kb FROM KhamBenh kb JOIN kb.nguoiDung nd WHERE nd.giaDinh.maGiaDinh = :maGiaDinh")
    List<KhamBenh> findByGiaDinh(@Param("maGiaDinh") String maGiaDinh);
}
