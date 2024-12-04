package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.dto.KhamBenhDTO;
import com.nhom27.nhatkykhambenh.dto.NguoiDungDTO;
import com.nhom27.nhatkykhambenh.model.KhamBenh;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IKhamBenhService {
    Page<KhamBenhDTO> getDSKhamBenh(Pageable pageable, String query);

    List<KhamBenh> getAll();

    List<KhamBenh> getAllByNguoiDung(NguoiDung nguoiDung);

    void saveKhamBenh(KhamBenhDTO khamBenhDTO);

    KhamBenhDTO findById(Integer id);

    void deleteById(Integer id);

    void deleteAllByIds(List<Integer> ids);

    List<KhamBenh> filterKhamBenh(String dateFrom, String dateTo, String maGiaDinh);
}
