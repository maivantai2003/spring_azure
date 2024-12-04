package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.model.GiaDinh;
import com.nhom27.nhatkykhambenh.model.TaiKhoan;
import com.nhom27.nhatkykhambenh.model.TongQuan;
import com.nhom27.nhatkykhambenh.repository.IGiaDinhRepo;
import com.nhom27.nhatkykhambenh.repository.ITongQuanRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.INguoiDungService;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.repository.INguoiDungRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.ITongQuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class NguoiDungService implements INguoiDungService {

    @Autowired
    private INguoiDungRepo nguoiDungRepo;

    @Autowired
    private ITongQuanRepo tongQuanRepo;

    @Autowired
    private IGiaDinhRepo giaDinhRepo;

    @Override
    public List<NguoiDung> getAllNguoiDung() {
        return nguoiDungRepo.findByTrangThai(true);
    }

    @Override
    public List<NguoiDung> getDsNguoiDungByGiaDinh(GiaDinh giaDinh) {
        return nguoiDungRepo.findByGiaDinhAndTrangThai(giaDinh, true);
    }

    @Override
    public void saveNguoiDung(NguoiDung nguoiDung, TaiKhoan taiKhoan) {
        try {
            TongQuan tongQuan = new TongQuan();

            nguoiDung.setGiaDinh(taiKhoan.getGiaDinh());
            nguoiDung.setTrangThai(true);
            nguoiDungRepo.save(nguoiDung);

            tongQuan.setNguoiDung(nguoiDung);
            tongQuanRepo.save(tongQuan);

//            GiaDinh giaDinh = taiKhoan.getGiaDinh();
//
//            if(nguoiDung.getMaNguoiDung() == null){
//                giaDinh.setSoLuong(giaDinh.getSoLuong() + 1);
//                giaDinhRepo.saveAndFlush(giaDinh);
//            }

        } catch (Exception e) {
            throw new SaveDataException("NguoiDung");
        }
    }

    @Override
    public void deleteById(Integer id) {
        NguoiDung nguoiDung = getById(id);
        nguoiDung.setTrangThai(false);
        nguoiDungRepo.save(nguoiDung);
    }

    @Override
    public NguoiDung getById(Integer id) {
        return nguoiDungRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    @Override
    public NguoiDung findByEmail(String email) {
        return nguoiDungRepo.findByEmail(email);
    }

}