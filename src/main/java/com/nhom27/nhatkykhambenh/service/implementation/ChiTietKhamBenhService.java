package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.dto.ThongTinBenhDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.mapper.ChiTietKhamBenhMapper;
import com.nhom27.nhatkykhambenh.model.*;
import com.nhom27.nhatkykhambenh.repository.*;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietKhamBenhService;
import com.nhom27.nhatkykhambenh.service.interfaces.IKhamBenhService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChiTietKhamBenhService implements IChiTietKhamBenhService {
    @Autowired
    private IChiTietKhamBenhRepo chiTietKhamBenhRepo;

    @Autowired
    private IKhamBenhRepo khamBenhRepo;

    @Autowired
    private IXetNghiemRepo xetNghiemRepo;

    @Autowired
    private IKhamBenhService khamBenhService;

    @Autowired
    private IHinhAnhRepo hinhAnhRepo;

    @Autowired
    private IChiTietDonThuocRepo chiTietDonThuocRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ChiTietKhamBenh> getDSChiTietKhamBenh(Pageable pageable, String query, Integer maKhamBenh) {
        String searchTerm = "%" + query + "%";

        String columnQuery = "SELECT GROUP_CONCAT(COLUMN_NAME) FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'chi_tiet_kham_benh' AND TABLE_SCHEMA = 'nhatkykhambenh'";
        Query columnNativeQuery = entityManager.createNativeQuery(columnQuery);
        String columns = (String) columnNativeQuery.getSingleResult();

        // Tạo truy vấn động với LIMIT và OFFSET
        String sql = "SELECT * FROM chi_tiet_kham_benh WHERE trang_thai = 1 AND ma_kham_benh = :maKhamBenh AND CONCAT(" + columns + ") LIKE :searchTerm " +
                "LIMIT :limit OFFSET :offset";
        Query nativeQuery = entityManager.createNativeQuery(sql, ChiTietKhamBenh.class);
        nativeQuery.setParameter("maKhamBenh", maKhamBenh);
        nativeQuery.setParameter("searchTerm", searchTerm);
        nativeQuery.setParameter("limit", pageable.getPageSize());
        nativeQuery.setParameter("offset", pageable.getPageNumber() * pageable.getPageSize());

        List<ChiTietKhamBenh> results = nativeQuery.getResultList();

        String countQuery = "SELECT COUNT(*) FROM chi_tiet_kham_benh WHERE CONCAT(" + columns + ") LIKE :searchTerm";
        Query countNativeQuery = entityManager.createNativeQuery(countQuery);
        countNativeQuery.setParameter("searchTerm", searchTerm);
        long totalElements = ((Number) countNativeQuery.getSingleResult()).longValue();

        return new PageImpl<>(results, pageable, totalElements);
    }

    @Override
    public List<ChiTietKhamBenh> getDSChiTietKhamBenh(Integer maKhamBenh) {
        KhamBenh khamBenh = khamBenhRepo.findById(maKhamBenh).get();

        return chiTietKhamBenhRepo.findAllByKhamBenh(khamBenh);
    }

    @Override
    public void saveChiTietKhamBenh(ChiTietKhamBenh ctKhamBenh, Integer maKhamBenh) {
        KhamBenh khamBenh = khamBenhRepo.findById(maKhamBenh).get();
        ctKhamBenh.setTrangThai(true);
        ctKhamBenh.setKhamBenh(khamBenh);
        try {
            chiTietKhamBenhRepo.save(ctKhamBenh);
        } catch (Exception e) {
            throw new SaveDataException("ChiTietKhamBenh");
        }
    }

    @Override
    public ChiTietKhamBenh findById(Integer id) {
        return chiTietKhamBenhRepo.findById(id).get();
    }

    @Override
    public void deleteById(Integer id) {
        ChiTietKhamBenh chiTietKhamBenh = findById(id);
        chiTietKhamBenh.setTrangThai(false);
        chiTietKhamBenhRepo.save(chiTietKhamBenh);
    }

    @Override
    public void deleteAllByIds(List<Integer> ids) {
        List<ChiTietKhamBenh> chitietdonThuocList = chiTietKhamBenhRepo.findAllById(ids);
        for (ChiTietKhamBenh chitietdonThuoc : chitietdonThuocList) {
            chitietdonThuoc.setTrangThai(false);
        }
        chiTietKhamBenhRepo.saveAll(chitietdonThuocList);
    }

    @Override
    public ThongTinBenhDTO getAllThongTinBenh(ChiTietKhamBenh chiTietKhamBenh) {
        KhamBenh khamBenh = khamBenhRepo.findByChiTietKhamBenh(chiTietKhamBenh.getMaChiTietKhamBenh());
        List<XetNghiem> dsXetNghiem = xetNghiemRepo.findByMaChiTietKhamBenh(chiTietKhamBenh.getMaChiTietKhamBenh());
        List<HinhAnh> dsHinhAnh = hinhAnhRepo.getHinhAnhsByChiTietKhamBenh(chiTietKhamBenh);
        List<ChiTietDonThuoc> dsChiTietDonThuoc = chiTietDonThuocRepo.findByMaChiTietKhamBenh(chiTietKhamBenh.getMaChiTietKhamBenh());

        ThongTinBenhDTO thongTinBenhDTO = new ThongTinBenhDTO();
        thongTinBenhDTO.setMaChiTietKhamBenh(chiTietKhamBenh.getMaChiTietKhamBenh());
        thongTinBenhDTO.setBacSi(chiTietKhamBenh.getBacSiKham());
        thongTinBenhDTO.setChiDinh(chiTietKhamBenh.getChiDinh());
        thongTinBenhDTO.setChuanDoan(chiTietKhamBenh.getChuanDoan());
        thongTinBenhDTO.setKhoaKham(chiTietKhamBenh.getKhoaKham());
        thongTinBenhDTO.setBenhVien(khamBenh.getBenhVien());
        thongTinBenhDTO.setNhomMau(chiTietKhamBenh.getNhomMau());
        thongTinBenhDTO.setTinhTrang(chiTietKhamBenh.getTinhTrang());
        thongTinBenhDTO.setNgayKhamChiTietKhamBenh(chiTietKhamBenh.getThoiGianVaoKham());
        thongTinBenhDTO.setDsXetNghiem(dsXetNghiem);
        thongTinBenhDTO.setDsHinhAnh(dsHinhAnh);
        thongTinBenhDTO.setDsChiTietDonThuoc(dsChiTietDonThuoc);

        return thongTinBenhDTO;
    }
}
