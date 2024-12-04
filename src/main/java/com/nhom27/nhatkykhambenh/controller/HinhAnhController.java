package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.model.HinhAnh;
import com.nhom27.nhatkykhambenh.service.implementation.HinhAnhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class HinhAnhController {

    @Autowired
    private HinhAnhService hinhAnhService;

    @GetMapping("admin/khambenh/chitiet/hinhanh")
    public String listHinhAnh(Model model, @RequestParam("maChiTietKhamBenh") int maChiTietKhamBenh) {
        List<HinhAnh> hinhAnhList = this.hinhAnhService.getHinhAnhByChiTietKhamBenh(maChiTietKhamBenh);
        model.addAttribute("hinhAnhList", hinhAnhList);
        return "admin/khambenh/listHinhAnh";
    }

    @PostMapping("admin/khambenh/chitiet/hinhanh/add")
    public String addHinhAnh(@RequestParam("image") MultipartFile[] imageFiles,
                             @RequestParam("maChiTietKhamBenh") int maChiTietKhamBenh) throws IOException {
        for (MultipartFile imageFile : imageFiles) {
            hinhAnhService.createHinhAnh(imageFile, maChiTietKhamBenh);
        }
        return String.format("redirect:/admin/khambenh/chitiet/hinhanh?maChiTietKhamBenh=%d", maChiTietKhamBenh);
    }

    @PostMapping("/admin/khambenh/chitiet/hinhanh/delete")
    public String deleteHinhAnh(@RequestParam("maHinhAnh") long maHinhAnh, @RequestParam("maChiTietKhamBenh")int maChiTietKhamBenh) {
        this.hinhAnhService.deleteHinhAnh(maHinhAnh);
        return String.format("redirect:/admin/khambenh/chitiet/hinhanh?maChiTietKhamBenh=%d", maChiTietKhamBenh);
    }
}
