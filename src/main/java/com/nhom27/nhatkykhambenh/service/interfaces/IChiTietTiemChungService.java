package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.model.ChiTietTiemChung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IChiTietTiemChungService {

    Page<ChiTietTiemChung> getCTTiemChungByTiemChung(Pageable pageable, String query, Integer maTiemChung);

    void saveTiemChung(ChiTietTiemChung ctTiemChung);

    ChiTietTiemChung findByIds(Integer maTiemChung, Integer maNguoiDung);

    void deleteByIds(Integer maTiemChung, Integer maNguoiDung);

    void deleteAllByIds(Integer maTiemChung, List<Integer> maNguoiDung);

    List<ChiTietTiemChung> getAllByNguoiDung(Integer maNguoiDung);

    Map<String, Integer> getVaccinationStats(Integer maTiemChung);

    List<ChiTietTiemChung> getAll();

    List<ChiTietTiemChung> filterChiTietTiemChung(String dateFrom, String dateTo, String maGiaDinh);
}
