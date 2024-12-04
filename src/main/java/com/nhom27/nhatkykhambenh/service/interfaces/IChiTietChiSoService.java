package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.dto.ChiTietChiSoDTO;

import java.util.Date;
import java.util.List;

public interface IChiTietChiSoService {
    List<ChiTietChiSoDTO> getDsChiTietChiSo(Integer maChiSo, Integer maTongQuan);

    void saveCTChiSo(ChiTietChiSoDTO chiTietChiSoDTO);

    void deleteChiTietChiSo(Integer maChiSo, Integer maTongQuan, Date thoiGianDo);
}
