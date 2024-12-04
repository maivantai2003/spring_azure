package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import com.nhom27.nhatkykhambenh.model.ChiTietTiemChung;
import com.nhom27.nhatkykhambenh.model.KhamBenh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IChiTietKhamBenhRepo extends JpaRepository<ChiTietKhamBenh, Integer> {
    List<ChiTietKhamBenh> findAllByKhamBenh(KhamBenh khamBenh);
}
