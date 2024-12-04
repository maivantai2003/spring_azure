package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.ChiTietChiSo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IChiTietChiSoRepo extends JpaRepository<ChiTietChiSo, ChiTietChiSo.ChiTietChiSoId> {
    List<ChiTietChiSo> findByMaTongQuanAndMaChiSo(Integer maTongQuan, Integer maChiSo);

    @Modifying
    @Transactional
    @Query("DELETE FROM ChiTietChiSo c WHERE c.maTongQuan = :maTongQuan AND c.maChiSo = :maChiSo AND c.thoiGianDo = :thoiGianDo")
    void deleteByMaTongQuanAndMaChiSoAndThoiGianDo(@Param("maTongQuan") Integer maTongQuan,
                                                   @Param("maChiSo") Integer maChiSo,
                                                   @Param("thoiGianDo") Date thoiGianDo);

    Optional<ChiTietChiSo> findTopByMaTongQuanAndMaChiSoOrderByThoiGianDoDesc(Integer maTongQuan, Integer maChiSo);
}
