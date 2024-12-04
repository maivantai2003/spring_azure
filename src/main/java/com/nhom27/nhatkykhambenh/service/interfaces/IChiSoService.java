package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.model.ChiSo;

import java.util.List;

public interface IChiSoService {
    ChiSo findByLoaiChiSo(String loaiChiSo);

    void deleteById(Integer id);

    List<ChiSo> getAllChiSo();
}
