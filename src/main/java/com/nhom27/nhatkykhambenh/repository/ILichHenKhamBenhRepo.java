package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.KhamBenh;
import com.nhom27.nhatkykhambenh.model.LichHenKham;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ILichHenKhamBenhRepo extends JpaRepository<LichHenKham, Long> {
    public LichHenKham findLichHenKhamByKhamBenh(KhamBenh khamBenh);
    @Query("SELECT lh FROM LichHenKham lh WHERE lh.khamBenh.nguoiDung = :nguoiDung ORDER BY lh.thoiGianHen DESC")
    List<LichHenKham> findLichHenKhamByNguoiDung(NguoiDung nguoiDung);
    public List<LichHenKham> findByThoiGianHenBetween(LocalDateTime start, LocalDateTime end);

}
