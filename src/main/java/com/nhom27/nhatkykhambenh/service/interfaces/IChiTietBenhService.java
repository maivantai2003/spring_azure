package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.model.ChiTietBenh;
import com.nhom27.nhatkykhambenh.model.TongQuan;

import java.util.List;

public interface IChiTietBenhService {
    void suyDienBenhTuTongQuan(TongQuan tongQuan);

    List<ChiTietBenh> getAllByTongQuan(TongQuan tongQuan, Boolean trangThai);

    List<ChiTietBenh> getAll();

    List<ChiTietBenh> filterChiTietBenh(String maGiaDinh);
}
