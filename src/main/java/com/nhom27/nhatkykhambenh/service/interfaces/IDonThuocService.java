package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.dto.DonThuocDTO;
import com.nhom27.nhatkykhambenh.model.DonThuoc;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDonThuocService {

    void saveDonThuoc(DonThuoc donThuoc);

    DonThuoc findById(Integer id);

    void deleteById(Integer id);

    void deleteAllByIds(List<Integer> ids);

    Page<DonThuoc> getDSDonThuoc(Pageable pageable, String query);

    List<DonThuoc> getAllDonThuoc();
}
