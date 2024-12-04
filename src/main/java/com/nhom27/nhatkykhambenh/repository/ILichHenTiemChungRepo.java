package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.LichHenTiemChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ILichHenTiemChungRepo extends JpaRepository<LichHenTiemChung, Integer> {
    @Query("SELECT l FROM LichHenTiemChung l LEFT JOIN FETCH l.nguoiDungTiemChungList WHERE l.maLichHenTiemChung = :id")
    Optional<LichHenTiemChung> findByIdWithNguoiDungTiemChungList(@Param("id") Integer id);

    List<LichHenTiemChung> findByNgayHenTiemBetween(LocalDateTime start, LocalDateTime end);
}
