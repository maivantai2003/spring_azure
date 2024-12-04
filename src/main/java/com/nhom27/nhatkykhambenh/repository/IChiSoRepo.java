package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.ChiSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChiSoRepo extends JpaRepository<ChiSo, Integer> {
    @Query("SELECT c FROM ChiSo c WHERE c.loaiChiSo = :loaiChiSo")
    ChiSo findByLoaiChiSo(@Param("loaiChiSo") String loaiChiSo);

    List<ChiSo> findAll();
}
