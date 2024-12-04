package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.dto.LichHenTiemChungDTO;
import com.nhom27.nhatkykhambenh.dto.NguoiDungTiemChungDTO;
import com.nhom27.nhatkykhambenh.model.LichHenTiemChung;
import com.nhom27.nhatkykhambenh.model.NguoiDungTiemChung;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ILichHenTiemChungService {
    public void CreateLichHenTiemChung(LichHenTiemChungDTO lichHenTiemChungDTO, Set<NguoiDungTiemChung> nguoiDungTiemChungSet);
    public void UpdateLichHenTiemChung(LichHenTiemChungDTO lichHenTiemChungDTO, Set<NguoiDungTiemChung> nguoiDungTiemChungSet);
    public void DeleteLichHenTiemChung(Integer id);
    public void DeleteLichHenTiemChungList(List<Integer> Ids);
    public List<LichHenTiemChungDTO> GetAllLichHenTiemChung();
    public LichHenTiemChungDTO GetLichHenTiemChungById(Integer id);
    public List<LichHenTiemChungDTO> GetLichHenTiemChungByNguoiDung(Integer nguoiDungId);
}

