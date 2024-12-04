package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.model.ChiTietTiemChung;
import com.nhom27.nhatkykhambenh.repository.IChiTietTiemChungRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietTiemChungService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChiTietTiemChungService implements IChiTietTiemChungService {

    @Autowired
    private IChiTietTiemChungRepo chiTietTiemChungRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ChiTietTiemChung> getCTTiemChungByTiemChung(Pageable pageable,
                                                            String query, 
                                                            Integer maTiemChung) {
        String searchTerm = "%" + query + "%";

        String columnQuery = "SELECT GROUP_CONCAT(COLUMN_NAME) FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'chi_tiet_tiem_chung' AND TABLE_SCHEMA = 'nhatkykhambenh'";
        Query columnNativeQuery = entityManager.createNativeQuery(columnQuery);
        String columns = (String) columnNativeQuery.getSingleResult();

        String sql = "SELECT * FROM chi_tiet_tiem_chung WHERE ma_tiem_chung= :maTiemChung AND CONCAT(" + columns + ") LIKE :searchTerm " +
                "LIMIT :limit OFFSET :offset";
        Query nativeQuery = entityManager.createNativeQuery(sql, ChiTietTiemChung.class);
        nativeQuery.setParameter("searchTerm", searchTerm);
        nativeQuery.setParameter("maTiemChung", maTiemChung);
        nativeQuery.setParameter("limit", pageable.getPageSize());
        nativeQuery.setParameter("offset", pageable.getPageNumber() * pageable.getPageSize());

        List<ChiTietTiemChung> results = nativeQuery.getResultList();

        String countQuery = "SELECT COUNT(*) FROM chi_tiet_tiem_chung WHERE CONCAT(" + columns + ") LIKE :searchTerm";
        Query countNativeQuery = entityManager.createNativeQuery(countQuery);
        countNativeQuery.setParameter("searchTerm", searchTerm);
        long totalElements = ((Number) countNativeQuery.getSingleResult()).longValue();

        return new PageImpl<>(results, pageable, totalElements);
    }

    @Override
    public void saveTiemChung(ChiTietTiemChung ctTiemChung) {
        ctTiemChung.setTrangThai(true);
        try {
            chiTietTiemChungRepo.save(ctTiemChung);
        } catch (Exception e) {
            throw new SaveDataException("ChiTietTiemChung");
        }
    }

    @Override
    public ChiTietTiemChung findByIds(Integer maTiemChung, Integer maNguoiDung) {
        return chiTietTiemChungRepo.findByMaTiemChungAndMaNguoiDung(maTiemChung, maNguoiDung).orElseThrow(() -> new SaveDataException("ChiTietTiemChung"));
    }

    @Override
    public void deleteByIds(Integer maTiemChung, Integer maNguoiDung) {
        ChiTietTiemChung chiTietTiemChung = findByIds(maTiemChung, maNguoiDung);
        chiTietTiemChung.setTrangThai(false);
        chiTietTiemChungRepo.save(chiTietTiemChung);
    }

    @Override
    public void deleteAllByIds(Integer maTiemChung, List<Integer> maNguoiDung) {
//        List<ChiTietTiemChung> chiTietTiemChungList = chiTietTiemChungRepo.findAllById(
//                maNguoiDung.stream()
//                        .map(maND -> new ChiTietTiemChung.ChiTietTiemChungId(maTiemChung, maND))
//                        .toList()
//        );
//
//        for (ChiTietTiemChung chiTietTiemChung : chiTietTiemChungList) {
//            chiTietTiemChung.setTrangThai(false);
//        }
//
//        chiTietTiemChungRepo.saveAll(chiTietTiemChungList);
    }

    @Override
    public List<ChiTietTiemChung> getAllByNguoiDung(Integer maNguoiDung) {
        return chiTietTiemChungRepo.findAllByMaNguoiDung(maNguoiDung);
    }

    @Override
    public Map<String, Integer> getVaccinationStats(Integer maTiemChung) {
        Integer daTiem = chiTietTiemChungRepo.countByMaTiemChungAndTrangThai(maTiemChung, true);
        Integer chuaTiem = chiTietTiemChungRepo.countByMaTiemChungAndTrangThai(maTiemChung, false);

        Map<String, Integer> stats = new HashMap<>();
        stats.put("Đã tiêm", daTiem);
        stats.put("Chưa tiêm", chuaTiem);
        return stats;
    }

    @Override
    public List<ChiTietTiemChung> getAll() {
        return chiTietTiemChungRepo.findAll();
    }

    @Override
    public List<ChiTietTiemChung> filterChiTietTiemChung(String dateFrom, String dateTo, String maGiaDinh) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate fromDate = (dateFrom != null && !dateFrom.isEmpty())
                ? LocalDate.parse(dateFrom, formatter)
                : null;
        LocalDateTime fromDateTime = (fromDate != null) ? fromDate.atStartOfDay() : null;

        LocalDate toDate = (dateTo != null && !dateTo.isEmpty())
                ? LocalDate.parse(dateTo, formatter)
                : null;
        LocalDateTime toDateTime = (toDate != null) ? toDate.atStartOfDay() : null;

        if (fromDateTime != null && toDateTime != null && maGiaDinh != null && !maGiaDinh.isEmpty()) {
            return chiTietTiemChungRepo.findByDateRangeAndGiaDinh(fromDateTime, toDateTime, maGiaDinh);
        } else if (fromDateTime != null && toDateTime != null) {
            return chiTietTiemChungRepo.findByDateRange(fromDateTime, toDateTime);
        } else if (maGiaDinh != null && !maGiaDinh.isEmpty()) {
            return chiTietTiemChungRepo.findByGiaDinh(maGiaDinh);
        } else {
            return chiTietTiemChungRepo.findAll();
        }
    }

}
