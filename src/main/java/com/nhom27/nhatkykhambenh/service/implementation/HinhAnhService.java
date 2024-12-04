package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import com.nhom27.nhatkykhambenh.model.HinhAnh;
import com.nhom27.nhatkykhambenh.repository.IChiTietKhamBenhRepo;
import com.nhom27.nhatkykhambenh.repository.IHinhAnhRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.ICloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class HinhAnhService {

    @Autowired
    private ICloudinaryService cloudinaryService;

    @Autowired
    private IChiTietKhamBenhRepo chiTietKhamBenhRepo;

    @Autowired
    private IHinhAnhRepo hinhAnhRepo;

    public void createHinhAnh(MultipartFile file, int maChiTietKhamBenh) throws IOException {
        HinhAnh hinhAnh = new HinhAnh();
        hinhAnh.setHinhAnh(cloudinaryService.uploadImage(file));
        ChiTietKhamBenh chiTietKhamBenh = chiTietKhamBenhRepo.findById(maChiTietKhamBenh).get();
        hinhAnh.setChiTietKhamBenh(chiTietKhamBenh);
        hinhAnhRepo.save(hinhAnh);
    }

    public List<HinhAnh> getHinhAnhByChiTietKhamBenh(int maChiTietKhamBenh) {
        ChiTietKhamBenh chiTietKhamBenh = chiTietKhamBenhRepo.findById(maChiTietKhamBenh).get();
        List<HinhAnh> hinhAnhList = this.hinhAnhRepo.getHinhAnhsByChiTietKhamBenh(chiTietKhamBenh);
        return hinhAnhList;
    }
    public void deleteHinhAnh(long maHinhAnh) {
        HinhAnh hinhAnh = this.hinhAnhRepo.findById(maHinhAnh).get();
        this.hinhAnhRepo.delete(hinhAnh);
    }
}
