package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.dto.LichHenKhamDTO;
import com.nhom27.nhatkykhambenh.model.LichHenKham;

import java.util.List;

public interface ILichHenKhamBenhService {
    public List<LichHenKhamDTO> getAllLichHenKhamBenh();
    public LichHenKhamDTO getLichHenKhamById(Long id);
    public void createLichHenKham(LichHenKhamDTO lichHenKhamDTO, Integer maKhamBenh);
    public LichHenKhamDTO getLichHenKhamByKhamBenh(Integer maKhamBenh);
    public List<LichHenKhamDTO> getLichHenKhamByNguoiDung(Integer maNguoiDung);
}
