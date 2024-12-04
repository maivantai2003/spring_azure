package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.KhamBenhDTO;
import com.nhom27.nhatkykhambenh.dto.LichHenKhamDTO;
import com.nhom27.nhatkykhambenh.model.LichHenKham;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.service.implementation.LichHenKhamBenhService;
import com.nhom27.nhatkykhambenh.service.interfaces.IKhamBenhService;
import com.nhom27.nhatkykhambenh.service.interfaces.ILichHenKhamBenhService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LichHenKhamBenhController {
    @Autowired
    private ILichHenKhamBenhService lichHenKhamBenhService;
    @Autowired
    private IKhamBenhService ikhamBenhService;
    @GetMapping("/admin/khambenh/lichhen")
    public String listLichHenKhamBenh(Model model, HttpSession session) {
        List<LichHenKhamDTO> lichHenKhamList = this.lichHenKhamBenhService.getAllLichHenKhamBenh();
        model.addAttribute("lichHenKhamList", lichHenKhamList);
        return "admin/khambenh/listLichHenKhamBenh";
    }

    @GetMapping("/admin/khambenh/lichhen/add")
    public String getLichHenKhamBenh(@RequestParam(value = "maLichHenKhamBenh", required = false) Long maLichHenKhamBenh, Model model) {
        LichHenKhamDTO lichHenKhamDTO = new LichHenKhamDTO();

        model.addAttribute("lichHenKhamDTO", lichHenKhamDTO);
        model.addAttribute("id", null);

        return "admin/khambenh/addLichHenKhamBenh";
    }

    @GetMapping("/admin/khambenh/lichhen/{id}")
    public String getLichHenKhamBenhByID(Model model, @PathVariable("id") Long id) {
//        LichHenKhamDTO lichHenKhamDTO = this.lichHenKhamBenhService.getLichHenKhamById(id);
//        model.addAttribute("lichHenKhamDTO", lichHenKhamDTO);
        model.addAttribute("id", id);
        return "admin/khambenh/addLichHenKhamBenh";
    }

    @GetMapping("admin/khambenh/{maKhamBenh}/lichhen")
    public ResponseEntity<LichHenKhamDTO> getLichHenKhamByKhamBenh(@PathVariable("maKhamBenh") Integer maKhamBenh) {
        LichHenKhamDTO lichHenKhamDTO = this.lichHenKhamBenhService.getLichHenKhamByKhamBenh(maKhamBenh);
        lichHenKhamDTO.setKhamBenh(null);
        return ResponseEntity.ok().body(lichHenKhamDTO);
    }

    @PostMapping("/admin/khambenh/{maKhamBenh}/lichhen")
    public ResponseEntity createLichHenKhamBenh(@RequestBody LichHenKhamDTO lichHenKhamBenh, @PathVariable("maKhamBenh") Integer maKhamBenh) {
        this.lichHenKhamBenhService.createLichHenKham(lichHenKhamBenh, maKhamBenh);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/khambenh/lichhen")
    public String getLichHenKhamByUser(Model model, @RequestParam("maNguoiDung") Integer maNguoiDung){
        List<LichHenKhamDTO> lichHenKhamDTOList = this.lichHenKhamBenhService.getLichHenKhamByNguoiDung(maNguoiDung);
        model.addAttribute("lichHenKhamList", lichHenKhamDTOList);
        return "users/lichHenKham";
    }
}
