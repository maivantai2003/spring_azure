package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.dto.NguoiDungDTO;
import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import com.nhom27.nhatkykhambenh.model.KhamBenh;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IXetNghiemService {

    void saveXetNghiem(XetNghiem xetNghiem, Integer maChiTietKhamBenh);

    XetNghiem findById(Integer id);

    void deleteById(Integer id);

    void deleteAllByIds(List<Integer> ids);

    Page<XetNghiem> getDSXetNghiem(Pageable pageable, String query, Integer maChiTietKhamBenh);

    List<XetNghiem> filterXetNghiem(String dateFrom, String dateTo, String maGiaDinh);

    List<XetNghiem> getAll();
}
