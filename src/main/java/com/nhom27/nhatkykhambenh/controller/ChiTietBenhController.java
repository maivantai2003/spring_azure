package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.ChiTietBenhDTO;
import com.nhom27.nhatkykhambenh.dto.TongQuanDTO;
import com.nhom27.nhatkykhambenh.mapper.ChiTietBenhMapper;
import com.nhom27.nhatkykhambenh.mapper.TongQuanMapper;
import com.nhom27.nhatkykhambenh.model.ChiTietBenh;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.TongQuan;
import com.nhom27.nhatkykhambenh.service.implementation.TongQuanService;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietBenhService;
import com.nhom27.nhatkykhambenh.service.interfaces.INguoiDungService;
import com.nhom27.nhatkykhambenh.service.interfaces.ITongQuanService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChiTietBenhController {
    @Autowired
    private IChiTietBenhService chiTietBenhService;

    @Autowired
    private ChiTietBenhMapper chiTietBenhMapper;

    @Autowired
    private INguoiDungService nguoiDungService;

    @Autowired
    private TongQuanMapper tongQuanMapper;

    @Autowired
    private ITongQuanService tongQuanService;

    @GetMapping("/users/tiensubenh")
    public String GetAllTongQuan(Model model, HttpSession session,
                                 @RequestParam("maNguoiDung") Integer maNguoiDung) {

        List<String> pageName = new ArrayList<>();
        pageName.add("Tiền sử bệnh");

        NguoiDung nguoiDung = nguoiDungService.getById(maNguoiDung);
        session.setAttribute("nguoidung", nguoiDung);

        TongQuan tongQuan = tongQuanService.findByNguoiDung(maNguoiDung);
        TongQuanDTO tongQuanDTO = tongQuanMapper.toTongQuanDTO(tongQuan);
        List<ChiTietBenhDTO> chiTietBenhDTOList = chiTietBenhMapper.toChiTietBenhDtoList(
                chiTietBenhService.getAllByTongQuan(tongQuan, true)
        );

        session.setAttribute("pageName", pageName);
        model.addAttribute("tongQuan", tongQuanDTO);
        model.addAttribute("dsChiTietBenh", chiTietBenhDTOList);

        return "users/tiensubenh";
    }

    @PostMapping("/users/tiensubenh/capnhatchitietbenh")
    public String capNhatChiTietBenh(@RequestParam("maTongQuan") Integer maTongQuan,
                                     RedirectAttributes redirectAttributes) {

        TongQuan tongQuan = tongQuanService.getById(maTongQuan);

        chiTietBenhService.suyDienBenhTuTongQuan(tongQuan);

        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật tiền sử bệnh thành công!");
        redirectAttributes.addAttribute("maNguoiDung", tongQuan.getNguoiDung().getMaNguoiDung());
        return "redirect:/users/tiensubenh";
    }
}
