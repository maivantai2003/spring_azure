package com.nhom27.nhatkykhambenh.repository;

import com.nhom27.nhatkykhambenh.model.TiemChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITiemChungRepo extends JpaRepository<TiemChung, Integer> {

}
