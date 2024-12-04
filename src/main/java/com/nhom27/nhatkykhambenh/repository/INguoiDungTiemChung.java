package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.LichHenTiemChung;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.NguoiDungTiemChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface INguoiDungTiemChung extends JpaRepository<NguoiDungTiemChung, Integer> {
    @Query("SELECT ndtc FROM NguoiDungTiemChung ndtc JOIN FETCH ndtc.nguoiDung WHERE ndtc.lichHenTiemChung = :lichHenTiemChung")
    List<NguoiDungTiemChung> findAllByLichHenTiemChung(@Param("lichHenTiemChung") LichHenTiemChung lichHenTiemChung);
    @Query("SELECT nd FROM NguoiDungTiemChung nd JOIN nd.lichHenTiemChung lh " +
            "WHERE nd.nguoiDung = :nguoiDung ORDER BY lh.ngayHenTiem DESC")
    List<NguoiDungTiemChung> findNguoiDungTiemChungByNguoiDungOrderByNgayHenTiem(@Param("nguoiDung") NguoiDung nguoiDung);
    void deleteByLichHenTiemChung(LichHenTiemChung lichHenTiemChung);
}
