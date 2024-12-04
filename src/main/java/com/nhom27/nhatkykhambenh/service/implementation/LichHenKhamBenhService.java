package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.dto.KhamBenhDTO;
import com.nhom27.nhatkykhambenh.dto.LichHenKhamDTO;
import com.nhom27.nhatkykhambenh.mapper.KhamBenhMapper;
import com.nhom27.nhatkykhambenh.mapper.LichHenKhamMapper;
import com.nhom27.nhatkykhambenh.mapper.LichHenTiemChungMapper;
import com.nhom27.nhatkykhambenh.model.KhamBenh;
import com.nhom27.nhatkykhambenh.model.LichHenKham;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.repository.ILichHenKhamBenhRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.IKhamBenhService;
import com.nhom27.nhatkykhambenh.service.interfaces.ILichHenKhamBenhService;
import com.nhom27.nhatkykhambenh.service.interfaces.INguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LichHenKhamBenhService implements ILichHenKhamBenhService {
    @Autowired
    ILichHenKhamBenhRepo lichHenKhamBenhRepo;
    @Autowired
    IKhamBenhService khamBenhService;
    @Autowired
    LichHenKhamMapper lichHenKhamMapper;
    @Autowired
    KhamBenhMapper khamBenhMapper;
@Autowired
    INguoiDungService nguoiDungService;
    public List<LichHenKhamDTO> getAllLichHenKhamBenh(){
        List<LichHenKham> lichHenKhamList = lichHenKhamBenhRepo.findAll();
        return lichHenKhamMapper.toListDTO(lichHenKhamList);
    }

    public LichHenKhamDTO getLichHenKhamById(Long id){
        Optional<LichHenKham> lichHenKham = this.lichHenKhamBenhRepo.findById(id);
        return lichHenKhamMapper.toDTO(lichHenKham.get());
    }

    public void createLichHenKham(LichHenKhamDTO lichHenKhamDTO,Integer id){
        KhamBenhDTO khamBenhDTO = this.khamBenhService.findById(id);
        KhamBenh khamBenh = khamBenhMapper.toKhamBenh(khamBenhDTO);
        LichHenKham lichHenKham = this.lichHenKhamBenhRepo.findLichHenKhamByKhamBenh(khamBenh);
        if (lichHenKham == null) {
            lichHenKhamDTO.setKhamBenh(khamBenhDTO);
            LichHenKham newLichHenKham = this.lichHenKhamMapper.toEntity(lichHenKhamDTO);
            this.lichHenKhamBenhRepo.save(newLichHenKham);
        }else{
            lichHenKham.setThoiGianHen(lichHenKhamDTO.getThoiGianHen());
            this.lichHenKhamBenhRepo.save(lichHenKham);
        }
    }
    public LichHenKhamDTO getLichHenKhamByKhamBenh(Integer maKhamBenh){
        KhamBenhDTO khamBenhDTO = this.khamBenhService.findById(maKhamBenh);
        KhamBenh khamBenh = khamBenhMapper.toKhamBenh(khamBenhDTO);
        LichHenKham lichHenKham = this.lichHenKhamBenhRepo.findLichHenKhamByKhamBenh(khamBenh);
        return lichHenKhamMapper.toDTO(lichHenKham);
    }

    public List<LichHenKhamDTO> getLichHenKhamByNguoiDung(Integer maNguoiDung){
        NguoiDung nguoiDung = this.nguoiDungService.getById(maNguoiDung);
        List<LichHenKham> lichHenKhamList = this.lichHenKhamBenhRepo.findLichHenKhamByNguoiDung(nguoiDung);
        return lichHenKhamMapper.toListDTO(lichHenKhamList);
    }
}
