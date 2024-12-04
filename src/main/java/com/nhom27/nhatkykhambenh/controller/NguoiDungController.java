package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.NguoiDungDTO;
import com.nhom27.nhatkykhambenh.mapper.NguoiDungMapper;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.TaiKhoan;
import com.nhom27.nhatkykhambenh.service.interfaces.ICloudinaryService;
import com.nhom27.nhatkykhambenh.service.interfaces.INguoiDungService;
import com.nhom27.nhatkykhambenh.service.interfaces.ITaiKhoanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NguoiDungController {

    @Autowired
    private INguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungMapper nguoiDungMapper;

    @Autowired
    private ITaiKhoanService taiKhoanService;

    @Autowired
    private ICloudinaryService cloudinaryService;

    @GetMapping()
    public String home(Model model) {
        TaiKhoan taiKhoan = taiKhoanService.getCurrentUser();

        List<NguoiDungDTO> dsNguoiDungDTO = nguoiDungMapper.toNguoiDungDtoList(
                nguoiDungService.getDsNguoiDungByGiaDinh(taiKhoan.getGiaDinh())
        );

        if(!dsNguoiDungDTO.isEmpty()) {
            model.addAttribute("dsNguoiDung", dsNguoiDungDTO);
        }

        return "users/danhsachnguoidung";
    }

    @GetMapping("/users/nguoidung/add")
    public String formThemNguoiThan(Model model) {
        NguoiDungDTO nguoiDungDTO = new NguoiDungDTO();

        model.addAttribute("nguoiDung", nguoiDungDTO);
        return "users/themnguoithan";
    }

    @GetMapping("/users/nguoidung/update")
    public String formSuaNguoiThan(@RequestParam("maNguoiDung") Integer maNguoiDung, Model model) {
        NguoiDungDTO nguoiDungDTO = nguoiDungMapper.toNguoiDungDTO(nguoiDungService.getById(maNguoiDung));

        model.addAttribute("nguoiDung", nguoiDungDTO);
        return "users/themnguoithan";
    }

    @PostMapping("/users/nguoidung/save")
    public String saveThemNguoiThan(@ModelAttribute("nguoiDung") NguoiDungDTO nguoiDungDTO,
                                    @RequestParam("file") MultipartFile file,
                                    HttpSession session) {
        TaiKhoan taiKhoan = (TaiKhoan) session.getAttribute("taikhoan");

        try {
            if (file != null && !file.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(file);
                nguoiDungDTO.setHinhAnh(imageUrl);
            }
            else if (nguoiDungDTO.getMaNguoiDung() != null) {
                NguoiDung existingNguoiDung = nguoiDungService.getById(nguoiDungDTO.getMaNguoiDung());
                nguoiDungDTO.setHinhAnh(existingNguoiDung.getHinhAnh());
            }
            else if(file == null || file.isEmpty()) {
                nguoiDungDTO.setHinhAnh(null);
            }
            nguoiDungService.saveNguoiDung(nguoiDungMapper.toNguoiDung(nguoiDungDTO), taiKhoan);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @PostMapping("/users/nguoidung/delete")
    public String deleteNguoiDung(@RequestParam("maNguoiDung") Integer maNguoiDung,
                                  RedirectAttributes redirectAttributes) {
        try {
            nguoiDungService.deleteById(maNguoiDung);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi!! Xóa thông tin người dùng thất bại");
        }
        return "redirect:/";
    }
}
