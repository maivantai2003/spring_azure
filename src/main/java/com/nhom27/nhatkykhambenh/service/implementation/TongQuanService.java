package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.model.ChiSo;
import com.nhom27.nhatkykhambenh.model.ChiTietChiSo;
import com.nhom27.nhatkykhambenh.model.TongQuan;
import com.nhom27.nhatkykhambenh.repository.IChiSoRepo;
import com.nhom27.nhatkykhambenh.repository.IChiTietChiSoRepo;
import com.nhom27.nhatkykhambenh.repository.ITongQuanRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.ITongQuanService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TongQuanService implements ITongQuanService {

    @Autowired
    private ITongQuanRepo tongQuanRepo;

    @Autowired
    private IChiSoRepo chiSoRepo;

    @Autowired
    private IChiTietChiSoRepo chiTietChiSoRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<TongQuan> getDSTongQuan(Pageable pageable, String query) {
        String searchTerm = "%" + query + "%";

        String columnQuery = "SELECT GROUP_CONCAT(COLUMN_NAME) FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'tong_quan' AND TABLE_SCHEMA = 'nhatkykhambenh'";
        Query columnNativeQuery = entityManager.createNativeQuery(columnQuery);
        String columns = (String) columnNativeQuery.getSingleResult();

        String sql = "SELECT * FROM tong_quan WHERE CONCAT(" + columns + ") LIKE :searchTerm " +
                "LIMIT :limit OFFSET :offset";
        Query nativeQuery = entityManager.createNativeQuery(sql, TongQuan.class);
        nativeQuery.setParameter("searchTerm", searchTerm);
        nativeQuery.setParameter("limit", pageable.getPageSize());
        nativeQuery.setParameter("offset", pageable.getPageNumber() * pageable.getPageSize());

        List<TongQuan> results = nativeQuery.getResultList();

        String countQuery = "SELECT COUNT(*) FROM tong_quan WHERE CONCAT(" + columns + ") LIKE :searchTerm";
        Query countNativeQuery = entityManager.createNativeQuery(countQuery);
        countNativeQuery.setParameter("searchTerm", searchTerm);
        long totalElements = ((Number) countNativeQuery.getSingleResult()).longValue();

        return new PageImpl<>(results, pageable, totalElements);
    }

    @Override
    public TongQuan findByNguoiDung(Integer maNguoiDung) {
        return tongQuanRepo.findByNguoiDung_MaNguoiDung(maNguoiDung);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public String[][] updateChiSoForTongQuan(TongQuan tongQuan) {
        List<ChiSo> listChiSo = chiSoRepo.findAll();
        String[][] arr = new String[listChiSo.size()][2];
        int index = 0;

        for (ChiSo cs : listChiSo) {
            arr[index][0] = cs.getLoaiChiSo();
            List<ChiTietChiSo> chiTietChiSoList = chiTietChiSoRepo.findByMaTongQuanAndMaChiSo(tongQuan.getMaTongQuan(), cs.getMaChiSo());

            if (!chiTietChiSoList.isEmpty()) {
                ChiTietChiSo latestChiTietChiSo = Collections.max(chiTietChiSoList, Comparator.comparing(ChiTietChiSo::getThoiGianDo));
                arr[index][1] = latestChiTietChiSo.getThoiGianDo().toString();
            }
            else {
                arr[index][1] = "Không có dữ liệu";
            }

            index++;
        }

        return arr;
    }

    @Override
    public List<TongQuan> getAllTongQuan() {
        return tongQuanRepo.findAll();
    }

    @Override
    public TongQuan getById(Integer id) {
        return tongQuanRepo.findById(id).get();
    }
}
