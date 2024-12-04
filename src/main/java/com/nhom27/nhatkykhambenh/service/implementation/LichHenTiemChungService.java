package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.dto.LichHenTiemChungDTO;
import com.nhom27.nhatkykhambenh.dto.NguoiDungTiemChungDTO;
import com.nhom27.nhatkykhambenh.mapper.LichHenTiemChungMapper;
import com.nhom27.nhatkykhambenh.mapper.NguoiDungTiemChungMapper;
import com.nhom27.nhatkykhambenh.model.LichHenTiemChung;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.NguoiDungTiemChung;
import com.nhom27.nhatkykhambenh.repository.ILichHenTiemChungRepo;
import com.nhom27.nhatkykhambenh.repository.INguoiDungRepo;
import com.nhom27.nhatkykhambenh.repository.INguoiDungTiemChung;
import com.nhom27.nhatkykhambenh.service.interfaces.ILichHenTiemChungService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;

@Service
public class LichHenTiemChungService implements ILichHenTiemChungService {
    @Autowired
    private ILichHenTiemChungRepo lichHenTiemChungRepo;

    @Autowired
    private INguoiDungTiemChung nguoiDungTiemChungRepo;

    @Autowired
    private LichHenTiemChungMapper lichHenTiemChungMapper;

    @Autowired
    private NguoiDungTiemChungMapper nguoiDungTiemChungMapper;

    @Autowired
    private INguoiDungRepo nguoiDungRepo;

    @Override
    public void CreateLichHenTiemChung(LichHenTiemChungDTO lichHenTiemChungDTO, Set<NguoiDungTiemChung> nguoiDungTiemChungSet) {
        LichHenTiemChung lichHenTiemChung = this.lichHenTiemChungMapper.toLichHenTiemChung(lichHenTiemChungDTO);
        LichHenTiemChung savedLichHenTiemChung = lichHenTiemChungRepo.save(lichHenTiemChung);
        nguoiDungTiemChungSet.forEach(nguoiDungTiemChung -> {
            nguoiDungTiemChung.setLichHenTiemChung(savedLichHenTiemChung);
        });
         nguoiDungTiemChungRepo.saveAll(nguoiDungTiemChungSet);
        return;
    }

    @Override
    @Transactional
    public void UpdateLichHenTiemChung(LichHenTiemChungDTO lichHenTiemChungDTO, Set<NguoiDungTiemChung> nguoiDungTiemChungSet) {
        LichHenTiemChung lichHenTiemChung = this.lichHenTiemChungMapper.toLichHenTiemChung(lichHenTiemChungDTO);
        lichHenTiemChung.setNguoiDungTiemChungList(nguoiDungTiemChungSet);
        LichHenTiemChung savedLichHenTiemChung = lichHenTiemChungRepo.save(lichHenTiemChung);
        nguoiDungTiemChungRepo.deleteByLichHenTiemChung(lichHenTiemChung);
        nguoiDungTiemChungSet.forEach(nguoiDungTiemChung -> {
            nguoiDungTiemChung.setLichHenTiemChung(savedLichHenTiemChung);
        });
        nguoiDungTiemChungRepo.saveAll(nguoiDungTiemChungSet);
    }

    @Override
    public void DeleteLichHenTiemChung(Integer id) {

    }

    @Override
    @Transactional
    public void DeleteLichHenTiemChungList(List<Integer> Ids){
        for (Integer id : Ids) {
            LichHenTiemChung lichHenTiemChung = lichHenTiemChungRepo.findById(id).get();
            this.nguoiDungTiemChungRepo.deleteByLichHenTiemChung(lichHenTiemChung);
        }
        this.lichHenTiemChungRepo.deleteAllByIdInBatch(Ids);
    }

    @Override
    public List<LichHenTiemChungDTO> GetAllLichHenTiemChung() {
        List<LichHenTiemChung> listLichHenTiemChung = lichHenTiemChungRepo.findAll();
        List<LichHenTiemChungDTO> listLichHenTiemChungDTO = this.lichHenTiemChungMapper.toListLichHenTiemChungDTO(listLichHenTiemChung);
        return listLichHenTiemChungDTO;
    }

    public List<LichHenTiemChungDTO> GetLichHenTiemChungByNguoiDung(Integer nguoiDungId){
        NguoiDung nguoiDung = this.nguoiDungRepo.findById(nguoiDungId).get();
        List<NguoiDungTiemChung> nguoiDungTiemChungList = this.nguoiDungTiemChungRepo.findNguoiDungTiemChungByNguoiDungOrderByNgayHenTiem(nguoiDung);
        List<LichHenTiemChungDTO> lichHenTiemChungDTOList = new ArrayList<>();
        for(NguoiDungTiemChung nguoiDungTiemChung: nguoiDungTiemChungList){
            lichHenTiemChungDTOList.add(this.lichHenTiemChungMapper.toLichHenTiemChungDTO(nguoiDungTiemChung.getLichHenTiemChung()));
        }
        return lichHenTiemChungDTOList;
    }

    @Override
    @Transactional
    public LichHenTiemChungDTO GetLichHenTiemChungById(Integer id) {
        LichHenTiemChung lichHenTiemChung = lichHenTiemChungRepo.findById(id).orElse(null);

        List<NguoiDungTiemChung> nguoiDungTiemChungList = nguoiDungTiemChungRepo.findAllByLichHenTiemChung(lichHenTiemChung);
        Set<NguoiDungTiemChung> nguoiDungTiemChungSet = new HashSet<>(nguoiDungTiemChungList);
        Set<NguoiDungTiemChungDTO> nguoiDungTiemChungDTOSet = nguoiDungTiemChungMapper.toNguoiDungTiemChungDTOSet(nguoiDungTiemChungSet);
        LichHenTiemChungDTO lichHenTiemChungDTO = lichHenTiemChungMapper.toLichHenTiemChungDTO(lichHenTiemChung);
        lichHenTiemChungDTO.setNguoiDungTiemChungList(nguoiDungTiemChungDTOSet);
        return lichHenTiemChungDTO;
    }
}
