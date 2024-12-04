package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.mapper.ChiSoMapper;
import com.nhom27.nhatkykhambenh.model.ChiSo;
import com.nhom27.nhatkykhambenh.repository.IChiSoRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiSoService implements IChiSoService {

    @Autowired
    private IChiSoRepo chiSoRepo;

    @Override
    public ChiSo findByLoaiChiSo(String loaiChiSo) {
        return chiSoRepo.findByLoaiChiSo(loaiChiSo);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<ChiSo> getAllChiSo() {
        return chiSoRepo.findAll();
    }
}
