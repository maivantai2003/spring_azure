package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.dto.ChiTietDonThuocDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.mapper.ChiTietDonThuocMapper;
import com.nhom27.nhatkykhambenh.model.ChiTietDonThuoc;
import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import com.nhom27.nhatkykhambenh.model.DonThuoc;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import com.nhom27.nhatkykhambenh.repository.IChiTietDonThuocRepo;
import com.nhom27.nhatkykhambenh.repository.IChiTietKhamBenhRepo;
import com.nhom27.nhatkykhambenh.repository.IDonThuocRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietDonThuocService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChiTietDonThuocService implements IChiTietDonThuocService {
    @Autowired
    private IChiTietDonThuocRepo ChiTietDonThuocRepo;

    @Autowired
    private ChiTietDonThuocMapper ChiTietDonThuocMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IDonThuocRepo donThuocRepo;


    @Override
    public Page<ChiTietDonThuoc> getDSChiTietDonThuoc(Pageable pageable, String query,Integer maChiTietKhamBenh) {
        String searchTerm = "%" + query + "%";

        String columnQuery = "SELECT GROUP_CONCAT(COLUMN_NAME) FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'chi_tiet_don_thuoc' AND TABLE_SCHEMA = 'nhatkykhambenh'";
        Query columnNativeQuery = entityManager.createNativeQuery(columnQuery);
        String columns = (String) columnNativeQuery.getSingleResult();

        // Tạo truy vấn động với LIMIT và OFFSET
        String sql = "SELECT * FROM chi_tiet_don_thuoc WHERE trang_thai = 1 AND ma_chi_tiet_kham_benh = :maChiTietKhamBenh AND CONCAT(" + columns + ") LIKE :searchTerm " +
                "LIMIT :limit OFFSET :offset";
        Query nativeQuery = entityManager.createNativeQuery(sql, ChiTietDonThuoc.class);
        nativeQuery.setParameter("maChiTietKhamBenh", maChiTietKhamBenh);
        nativeQuery.setParameter("searchTerm", searchTerm);
        nativeQuery.setParameter("limit", pageable.getPageSize());
        nativeQuery.setParameter("offset", pageable.getPageNumber() * pageable.getPageSize());

        List<ChiTietDonThuoc> results = nativeQuery.getResultList();

        String countQuery = "SELECT COUNT(*) FROM chi_tiet_don_thuoc WHERE CONCAT(" + columns + ") LIKE :searchTerm";
        Query countNativeQuery = entityManager.createNativeQuery(countQuery);
        countNativeQuery.setParameter("searchTerm", searchTerm);
        long totalElements = ((Number) countNativeQuery.getSingleResult()).longValue();

        return new PageImpl<>(results, pageable, totalElements);
    }


    @Override
    public void saveChiTietDonThuoc(ChiTietDonThuoc chiTietDonThuoc, Integer maDonThuoc, Integer maChiTietKhamBenh) {
        chiTietDonThuoc.setMaDonThuoc(maDonThuoc);
        chiTietDonThuoc.setMaChiTietKhamBenh(maChiTietKhamBenh);
        chiTietDonThuoc.setTrangThai(true);
        try {
            ChiTietDonThuocRepo.save(chiTietDonThuoc);
        } catch (Exception e) {
            throw new SaveDataException("Lỗi khi lưu ChiTietDonThuoc: " + e.getMessage());
        }
    }


    @Override
    public ChiTietDonThuoc findById(Integer maDonThuoc, Integer maChiTietKhamBenh) {
        return ChiTietDonThuocRepo.findByMaDonThuocAndMaChiTietKhamBenh(maDonThuoc, maChiTietKhamBenh)
                .orElseThrow(() -> new SaveDataException("ChiTietTiemChung"));
    }

    @Override
    public void deleteById(Integer maDonThuoc, Integer maChiTietKhamBenh) {
        ChiTietDonThuoc chiTietDonThuoc = findById(maDonThuoc, maChiTietKhamBenh);
        chiTietDonThuoc.setTrangThai(false);
        ChiTietDonThuocRepo.save(chiTietDonThuoc);
    }


    @Override
    public void deleteAllByIds(Integer maChiTietKhamBenh, List<Integer> maDonThuoc) {
        List<ChiTietDonThuoc> chiTietDonThuocList = ChiTietDonThuocRepo.findByMaDonThuocIn(maDonThuoc);

        for (ChiTietDonThuoc chiTietDonThuoc : chiTietDonThuocList) {
            chiTietDonThuoc.setTrangThai(false);
        }

        ChiTietDonThuocRepo.saveAll(chiTietDonThuocList);
    }

}

