package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.dto.TiemChungDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.mapper.TiemChungMapper;
import com.nhom27.nhatkykhambenh.model.ChiTietTiemChung;
import com.nhom27.nhatkykhambenh.model.TiemChung;
import com.nhom27.nhatkykhambenh.repository.IChiTietTiemChungRepo;
import com.nhom27.nhatkykhambenh.repository.ITiemChungRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.ITiemChungService;
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
import java.util.Map;

@Service
public class TiemChungService implements ITiemChungService {

    @Autowired
    private ITiemChungRepo tiemChungRepo;

    @Autowired
    private TiemChungMapper tiemChungMapper;

    @Autowired
    private IChiTietTiemChungRepo chiTietTiemChungRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<TiemChungDTO> getDSTiemChung(Pageable pageable, String query) {
        String searchTerm = "%" + query + "%";

        String columnQuery = "SELECT GROUP_CONCAT(COLUMN_NAME) FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'tiem_chung' AND TABLE_SCHEMA = 'nhatkykhambenh'";
        Query columnNativeQuery = entityManager.createNativeQuery(columnQuery);
        String columns = (String) columnNativeQuery.getSingleResult();

        String sql = "SELECT * FROM tiem_chung WHERE CONCAT(" + columns + ") LIKE :searchTerm " +
                "LIMIT :limit OFFSET :offset";
        Query nativeQuery = entityManager.createNativeQuery(sql, TiemChung.class);
        nativeQuery.setParameter("searchTerm", searchTerm);
        nativeQuery.setParameter("limit", pageable.getPageSize());
        nativeQuery.setParameter("offset", pageable.getPageNumber() * pageable.getPageSize());

        List<TiemChung> results = nativeQuery.getResultList();

        String countQuery = "SELECT COUNT(*) FROM tiem_chung WHERE CONCAT(" + columns + ") LIKE :searchTerm";
        Query countNativeQuery = entityManager.createNativeQuery(countQuery);
        countNativeQuery.setParameter("searchTerm", searchTerm);
        long totalElements = ((Number) countNativeQuery.getSingleResult()).longValue();

        return new PageImpl<>(tiemChungMapper.toTiemChungDtoList(results), pageable, totalElements);
    }

    @Override
    public List<TiemChungDTO> getAllTiemChung() {
        return tiemChungMapper.toTiemChungDtoList(tiemChungRepo.findAll());
    }

    @Override
    public void saveTiemChung(TiemChungDTO tiemChungDTO) {
        TiemChung tiemChung = tiemChungMapper.toTiemChung(tiemChungDTO);
        tiemChung.setTrangThai(true);
        try {
            tiemChungRepo.save(tiemChung);
        } catch (Exception e) {
            throw new SaveDataException(TiemChung.OBJ_NAME);
        }
    }

    @Override
    public TiemChungDTO findById(Integer id) {
        TiemChung tiemChung = tiemChungRepo.findById(id).get();
        return tiemChungMapper.toTiemChungDTO(tiemChung);
    }

    @Override
    public void deleteById(Integer id) {
        TiemChung tiemChung = tiemChungRepo.findById(id).orElseThrow(() -> new SaveDataException(TiemChung.OBJ_NAME));
        tiemChung.setTrangThai(false);
        tiemChungRepo.save(tiemChung);
    }

    @Override
    public void deleteAllByIds(List<Integer> ids) {
        List<TiemChung> tiemChungList = tiemChungRepo.findAllById(ids);
        for (TiemChung tiemChung : tiemChungList) {
            tiemChung.setTrangThai(false);
        }
        tiemChungRepo.saveAll(tiemChungList);
    }
}
