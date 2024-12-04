package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.LichHenTiemChungDTO;
import com.nhom27.nhatkykhambenh.dto.NguoiDungDTO;
import com.nhom27.nhatkykhambenh.dto.NguoiDungTiemChungDTO;
import com.nhom27.nhatkykhambenh.mapper.NguoiDungMapper;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.NguoiDungTiemChung;
import com.nhom27.nhatkykhambenh.model.NguoiDungTiemChungId;
import com.nhom27.nhatkykhambenh.model.TaiKhoan;
import com.nhom27.nhatkykhambenh.repository.INguoiDungRepo;
import com.nhom27.nhatkykhambenh.service.implementation.NguoiDungService;
import com.nhom27.nhatkykhambenh.service.interfaces.ILichHenTiemChungService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LichHenTiemChungController {

    @Autowired
    private ILichHenTiemChungService lichHenTiemChungService;
    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private NguoiDungMapper nguoiDungMapper;

    @GetMapping("/admin/lichhentiemchung/add")
    public String AddLichHenTiemChung(Model model, HttpSession session) {
        LichHenTiemChungDTO lichHenTiemChungDTO = new LichHenTiemChungDTO();
        lichHenTiemChungDTO.setNguoiDungTiemChungList(new HashSet<>());
        model.addAttribute("lichHenTiemChungDTO", lichHenTiemChungDTO);
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("taikhoan");
        List<NguoiDung> nguoiDungs = this.nguoiDungService.getAllNguoiDung();
        List<NguoiDungDTO> nguoiDungDTOs = nguoiDungMapper.toNguoiDungDtoList(nguoiDungs);
        model.addAttribute("members", nguoiDungDTOs);
        return "/admin/lichhentiemchung/addLichHenTiemChung";
    }

    @PostMapping("/admin/lichhentiemchung/save")
    public String CreateLichHenTiemChung(@ModelAttribute("lichhentiemchung") LichHenTiemChungDTO lichHenTiemChungDTO, @RequestParam("memberIds") List<Integer> memberIds, @RequestParam("tenVaccins") List<String> tenVaccins) {
        Set<NguoiDungTiemChung> nguoiDungTiemChungSet = new HashSet<>();
        for (int i = 0; i < memberIds.size(); i++) {
            nguoiDungTiemChungSet.add(NguoiDungTiemChung.builder()
                    .tenVaccin(tenVaccins.get(i))
                    .nguoiDung(NguoiDung.builder().maNguoiDung(memberIds.get(i)).build())
                    .build());
        }
        this.lichHenTiemChungService.CreateLichHenTiemChung(lichHenTiemChungDTO, nguoiDungTiemChungSet);
        return "redirect:/admin/lichhentiemchung";
    }

    @PostMapping("/admin/lichhentiemchung/delete")
    public String DeleteLichHenTiemChung(@RequestParam("deletedIds") List<Integer> deletedIds) {
        this.lichHenTiemChungService.DeleteLichHenTiemChungList(deletedIds);
        return "redirect:/admin/lichhentiemchung";
    }

    @GetMapping("/admin/lichhentiemchung")
    public String ListLichHenTiemChung(Model model) {
        List<LichHenTiemChungDTO> listLichHenTiemChungDTO = this.lichHenTiemChungService.GetAllLichHenTiemChung();
        model.addAttribute("listLichHenTiemChung", listLichHenTiemChungDTO);
        return "/admin/lichhentiemchung/listLichHenTiemChung";
    }

    @GetMapping("/admin/lichhentiemchung/{id}")
    public String GetLichHenTiemChungById(@PathVariable("id") Integer id, Model model, HttpSession session) {
        LichHenTiemChungDTO lichHenTiemChungDTO = this.lichHenTiemChungService.GetLichHenTiemChungById(id);
        System.out.println(lichHenTiemChungDTO.getNgayHenTiem());
        model.addAttribute("lichHenTiemChung",lichHenTiemChungDTO);
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("taikhoan");
        List<NguoiDung> nguoiDungs = this.nguoiDungService.getAllNguoiDung();
        List<NguoiDungDTO> nguoiDungDTOs = nguoiDungMapper.toNguoiDungDtoList(nguoiDungs);
        model.addAttribute("members", nguoiDungDTOs);
        System.out.println(lichHenTiemChungDTO.getNgayHenTiem());
        return "/admin/lichhentiemchung/updateLichHenTiemChung";
    }

    @PostMapping("/admin/lichhentiemchung/update")
    public String UpdateLichHenTiemChungById(@ModelAttribute("lichHenTiemChung") LichHenTiemChungDTO lichHenTiemChungDTO, @RequestParam("memberIds") List<Integer> memberIds, @RequestParam("tenVaccins") List<String> tenVaccins){
        Set<NguoiDungTiemChung> nguoiDungTiemChungSet = new HashSet<>();
        for (int i = 0; i < memberIds.size(); i++) {
            nguoiDungTiemChungSet.add(NguoiDungTiemChung.builder()
                    .tenVaccin(tenVaccins.get(i))
                    .nguoiDung(NguoiDung.builder().maNguoiDung(memberIds.get(i)).build())
                    .build());
        }
        this.lichHenTiemChungService.UpdateLichHenTiemChung(lichHenTiemChungDTO, nguoiDungTiemChungSet);
        return "redirect:/admin/lichhentiemchung";
    }

    @GetMapping("/users/tiemchung/lichhen")
    public String GetLichHenTiemChung(Model model, @RequestParam("maNguoiDung") Integer maNguoiDung, HttpSession session) {
        NguoiDung nguoiDung = this.nguoiDungService.getById(maNguoiDung);
        List<LichHenTiemChungDTO> lichHenTiemChungDTOList = this.lichHenTiemChungService.GetLichHenTiemChungByNguoiDung(maNguoiDung);
        for (LichHenTiemChungDTO lichHen : lichHenTiemChungDTOList) {
            LocalDateTime now = LocalDateTime.now();
            if (lichHen.getNgayHenTiem().isBefore(now)) {
                lichHen.setTrangThai("opacity-50"); // Đã qua
            } else if (lichHen.getNgayHenTiem().toLocalDate().equals(now.toLocalDate())) {
                lichHen.setTrangThai("bg-primary text-white"); // Hôm nay
            } else {
                lichHen.setTrangThai(""); // Bình thường
            }
        }
        model.addAttribute("lichHenTiemChungList", lichHenTiemChungDTOList);
        session.setAttribute("nguoidung", nguoiDung);
        return "users/lichhentiemchung";
    }
}
