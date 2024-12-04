package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.dto.GiaDinhDTO;
import com.nhom27.nhatkykhambenh.model.GiaDinh;
import com.nhom27.nhatkykhambenh.repository.IGiaDinhRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.IGiaDinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiaDinhService implements IGiaDinhService {

    @Autowired
    private IGiaDinhRepo giaDinhRepo;

    @Override
    public void saveGiaDinh(GiaDinh giaDinh) {
        try{
            giaDinhRepo.save(giaDinh);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public GiaDinhDTO findById(Integer id) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<GiaDinh> getAll() {
        return giaDinhRepo.findAll();
    }
}
