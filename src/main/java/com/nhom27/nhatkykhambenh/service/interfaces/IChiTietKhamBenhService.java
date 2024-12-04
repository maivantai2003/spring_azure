package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.dto.ThongTinBenhDTO;
import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IChiTietKhamBenhService {
    void saveChiTietKhamBenh(ChiTietKhamBenh chiTietKhamBenh, Integer maKhamBenh);

    ChiTietKhamBenh findById(Integer id);

    void deleteById(Integer id);

    void deleteAllByIds(List<Integer> ids);

    Page<ChiTietKhamBenh> getDSChiTietKhamBenh(Pageable pageable, String query, Integer maKhamBenh);

    List<ChiTietKhamBenh> getDSChiTietKhamBenh(Integer maKhamBenh);

    ThongTinBenhDTO getAllThongTinBenh(ChiTietKhamBenh chiTietKhamBenh);

}
