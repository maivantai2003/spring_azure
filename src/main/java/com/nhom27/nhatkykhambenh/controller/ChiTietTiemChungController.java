package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.ChiTietTiemChungDTO;
import com.nhom27.nhatkykhambenh.dto.NguoiDungDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.mapper.ChiTietTiemChungMapper;
import com.nhom27.nhatkykhambenh.mapper.NguoiDungMapper;
import com.nhom27.nhatkykhambenh.model.ChiTietTiemChung;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietTiemChungService;
import com.nhom27.nhatkykhambenh.service.interfaces.INguoiDungService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChiTietTiemChungController {

    @Autowired
    private IChiTietTiemChungService chiTietTiemChungService;

    @Autowired
    private ChiTietTiemChungMapper chiTietTiemChungMapper;

    @Autowired
    private INguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungMapper nguoiDungMapper;

    @GetMapping("/admin/tiemchung/chitiet")
    public String GetListTiemChung(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(defaultValue = "") String query,
                                   @RequestParam Integer maTiemChung) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChiTietTiemChung> chiTietTiemChungPage = chiTietTiemChungService.getCTTiemChungByTiemChung(pageable, query, maTiemChung);
        List<ChiTietTiemChungDTO> chiTietTiemChungDTOList = chiTietTiemChungMapper.toChiTietTiemChungDtoList(chiTietTiemChungPage.getContent());

        model.addAttribute("dsCTTiemChung", chiTietTiemChungDTOList);
        model.addAttribute("maTiemChung", maTiemChung);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", chiTietTiemChungPage.getTotalPages());
        model.addAttribute("totalItems", chiTietTiemChungPage.getTotalElements());
        model.addAttribute("query", query);

        int startItem = page * size + 1;
        int endItem = Math.min(startItem + size - 1, (int) chiTietTiemChungPage.getTotalElements());

        model.addAttribute("startItem", startItem);
        model.addAttribute("endItem", endItem);
        model.addAttribute("currentCount", endItem - startItem + 1);

        return "admin/tiemchung/listChiTietTiemChung";
    }

    @GetMapping("/admin/tiemchung/chitiet/add")
    public String addTiemChungForm(Model model, @RequestParam Integer maTiemChung) {
        ChiTietTiemChungDTO chiTietTiemChungDTO = new ChiTietTiemChungDTO();
        chiTietTiemChungDTO.setMaTiemChung(maTiemChung);

        List<NguoiDungDTO> nguoiDungDTOList = nguoiDungMapper.toNguoiDungDtoList(nguoiDungService.getAllNguoiDung());

        System.out.println("size = " + nguoiDungDTOList.size());

        for(NguoiDungDTO dt: nguoiDungDTOList){
            System.out.println("name " + dt.getTenNguoiDung());
        }

        model.addAttribute("ctTiemChung", chiTietTiemChungDTO);
        model.addAttribute("dsNguoiDung", nguoiDungDTOList);

        return "admin/tiemchung/addChiTietTiemChung";
    }

    @GetMapping("/admin/tiemchung/chitiet/update")
    public String updateTiemChungForm(Model model, @RequestParam Integer maTiemChung) {
        ChiTietTiemChungDTO chiTietTiemChungDTO = new ChiTietTiemChungDTO();
        chiTietTiemChungDTO.setMaTiemChung(maTiemChung);

        List<NguoiDungDTO> nguoiDungDTOList = nguoiDungMapper.toNguoiDungDtoList(nguoiDungService.getAllNguoiDung());

        model.addAttribute("ctTiemChung", chiTietTiemChungDTO);
        model.addAttribute("dsNguoiDung", nguoiDungDTOList);

        return "admin/tiemchung/addChiTietTiemChung";
    }

    @PostMapping("/admin/tiemchung/chitiet/save")
    public String saveTiemChung(@ModelAttribute("ctTiemChung") ChiTietTiemChungDTO ctTiemChungDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/tiemchung/addChiTietTiemChung";
        }
        try {
            ChiTietTiemChung ctTiemChung = chiTietTiemChungMapper.toChiTietTiemChung(ctTiemChungDTO);
            chiTietTiemChungService.saveTiemChung(ctTiemChung);
            redirectAttributes.addAttribute("maTiemChung", ctTiemChungDTO.getMaTiemChung());
            return "redirect:/admin/tiemchung/chitiet";
        } catch (SaveDataException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/tiemchung/addChiTietTiemChung";
        }
    }

    @PostMapping("/admin/tiemchung/chitiet/delete")
    public String deleteTiemChung(@RequestParam("maTiemChung") Integer maTiemChung,
                                  @RequestParam("maNguoiDung") Integer maNguoiDung,
                                  RedirectAttributes redirectAttributes) {
        try {
            chiTietTiemChungService.deleteByIds(maTiemChung, maNguoiDung);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi!! Xóa thông tin tiêm chủng thất bại");
        }

        redirectAttributes.addAttribute("maTiemChung", maTiemChung);

        return "redirect:/admin/tiemchung/chitiet";
    }

    @GetMapping("/users/tiemchung")
    public String getAllTiemChung(Model model,
                                  HttpSession session,
                                  @RequestParam("maNguoiDung") Integer maNguoiDung) {
        List<String> pageName = new ArrayList<>();
        pageName.add("Tiêm chủng");

        NguoiDung nguoiDung = nguoiDungService.getById(maNguoiDung);
        session.setAttribute("nguoidung", nguoiDung);

        List<ChiTietTiemChungDTO> dsChiTietTiemChungDTO = chiTietTiemChungMapper.toChiTietTiemChungDtoList(
            chiTietTiemChungService.getAllByNguoiDung(maNguoiDung)
        );

        session.setAttribute("pageName", pageName);
        model.addAttribute("dsChiTietTiemChung", dsChiTietTiemChungDTO);

        return "users/danhsachtiemchung";
    }

    @GetMapping("users/tiemchung/chitiet")
    public String chiTietBenh(Model model,
                              @RequestParam("maTiemChung") Integer maTiemChung,
                              HttpSession session) {

        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoidung");
        ChiTietTiemChungDTO chiTietTiemChungDTO = chiTietTiemChungMapper.toChiTietTiemChungDTO(
            chiTietTiemChungService.findByIds(maTiemChung, nguoiDung.getMaNguoiDung())
        );

        model.addAttribute("chiTietTiemChung", chiTietTiemChungDTO);

        return "users/thongtintiemchung";
    }

    @PostMapping("admin/tiemchung/chitiet/updateTrangThai")
    public String updateStatus(@RequestParam("maTiemChung") int maTiemChung,
                               @RequestParam("maNguoiDung") int maNguoiDung,
                               RedirectAttributes redirectAttributes) {
        try {
            ChiTietTiemChung chiTietTiemChung = chiTietTiemChungService.findByIds(maTiemChung, maNguoiDung);
            if (chiTietTiemChung == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy bản ghi.");
                redirectAttributes.addAttribute("maTiemChung", maTiemChung);
                return "redirect:/admin/tiemchung/chitiet";
            }

            if (chiTietTiemChung.getTrangThai()) {
                redirectAttributes.addFlashAttribute("error", "Trạng thái 'Đã tiêm' không thể thay đổi.");
                redirectAttributes.addAttribute("maTiemChung", maTiemChung);
                return "redirect:/admin/tiemchung/chitiet";
            }

            chiTietTiemChungService.saveTiemChung(chiTietTiemChung);

            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái thành công!");
            redirectAttributes.addAttribute("maTiemChung", maTiemChung);
            return "redirect:/admin/tiemchung/chitiet";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            redirectAttributes.addAttribute("maTiemChung", maTiemChung);
            return "redirect:/admin/tiemchung/chitiet";
        }
    }

}
