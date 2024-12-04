package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.ChiTietKhamBenhDTO;
import com.nhom27.nhatkykhambenh.dto.ThongTinBenhDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.mapper.ChiTietKhamBenhMapper;
import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.repository.IChiTietKhamBenhRepo;
import com.nhom27.nhatkykhambenh.service.implementation.ChiTietKhamBenhService;
import com.nhom27.nhatkykhambenh.service.implementation.ChiTietKhamBenhService;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietKhamBenhService;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChiTietKhamBenhController {

    @Autowired
    private IChiTietKhamBenhService chiTietKhamBenhService;

    @Autowired
    private ChiTietKhamBenhMapper chiTietKhamBenhMapper;

    @GetMapping("/admin/khambenh/chitiet")
    public String GetListChiTietKhamBenh(Model model,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size,
                                         @RequestParam(defaultValue = "") String query,
                                         @RequestParam Integer maKhamBenh) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChiTietKhamBenh> chitietkhamBenhPage = chiTietKhamBenhService.getDSChiTietKhamBenh(pageable, query, maKhamBenh);
        List<ChiTietKhamBenhDTO> chiTietKhamBenhDTOList = chiTietKhamBenhMapper.toChiTietKhamBenhDtoList(chitietkhamBenhPage.getContent());

        System.out.println("size = " + chitietkhamBenhPage.getContent().size());

        model.addAttribute("dsChiTietKhamBenh", chiTietKhamBenhDTOList);
        model.addAttribute("maKhamBenh", maKhamBenh);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", chitietkhamBenhPage.getTotalPages());
        model.addAttribute("totalItems", chitietkhamBenhPage.getTotalElements());

        model.addAttribute("query", query);

        int startItem = page * size + 1;
        int endItem = Math.min(startItem + size - 1, (int) chitietkhamBenhPage.getTotalElements());

        model.addAttribute("startItem", startItem);
        model.addAttribute("endItem", endItem);
        model.addAttribute("currentCount", endItem - startItem + 1);

        return "admin/khambenh/listChiTietKhamBenh";
    }

    @GetMapping("/admin/khambenh/chitiet/add")
    public String addChiTietKhamBenhForm(Model model, @RequestParam Integer maKhamBenh) {
        ChiTietKhamBenhDTO chiTietKhamBenhDTO = new ChiTietKhamBenhDTO();
        chiTietKhamBenhDTO.setMaKhamBenh(maKhamBenh);

        model.addAttribute("chitietkhambenh", chiTietKhamBenhDTO);
        model.addAttribute("maKhamBenh2", maKhamBenh);

        return "admin/khambenh/addChiTietKhamBenh";
    }


    @GetMapping("/admin/khambenh/chitiet/update")
    public String updateChiTietKhamBenhForm(@RequestParam Integer maChiTietKhamBenh,
                                            @RequestParam Integer maKhamBenh,
                                            Model model) {
        ChiTietKhamBenh chiTietKhamBenh = chiTietKhamBenhService.findById(maChiTietKhamBenh);
        ChiTietKhamBenhDTO chiTietKhamBenhDTO = chiTietKhamBenhMapper.toChiTietKhamBenhDTO(chiTietKhamBenh);
        chiTietKhamBenhDTO.setMaKhamBenh(maKhamBenh);

        model.addAttribute("chitietkhambenh", chiTietKhamBenhDTO);
        model.addAttribute("maKhamBenh2", maKhamBenh);
        return "admin/khambenh/addChiTietKhamBenh";
    }

    @PostMapping("/admin/khambenh/chitiet/save")
    public String saveChiTietKhamBenh(@ModelAttribute("chitietkhambenh") ChiTietKhamBenhDTO chitietkhambenhDTO,
                                      BindingResult bindingResult,
                                      Model model,
                                      @RequestParam(required = false) Integer maKhamBenh,
                                      @RequestParam(required = false) Integer maChiTietKhamBenh,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "admin/khambenh/addChiTietKhamBenh";
        }

        try {
            if (maChiTietKhamBenh != null) {
                chitietkhambenhDTO.setMaChiTietKhamBenh(maChiTietKhamBenh);
            }
            ChiTietKhamBenh chiTietKhamBenh = chiTietKhamBenhMapper.toChiTietKhamBenh(chitietkhambenhDTO);
            chiTietKhamBenhService.saveChiTietKhamBenh(chiTietKhamBenh, maKhamBenh);

            redirectAttributes.addAttribute("maKhamBenh", maKhamBenh);
            return "redirect:/admin/khambenh/chitiet";
        } catch (SaveDataException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/khambenh/addChiTietKhamBenh";
        }
    }


    @PostMapping("/admin/khambenh/chitiet/delete")
        public String deleteChiTietKhamBenh(@RequestParam("maChiTietKhamBenh") Integer maChiTietKhamBenh,
                                        @RequestParam("maKhamBenh") Integer maKhamBenh,
                                        RedirectAttributes redirectAttributes) {
        try {
            chiTietKhamBenhService.deleteById(maChiTietKhamBenh);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi!! Xóa thông tin Đơn Thuốc thất bại");
        }
        redirectAttributes.addAttribute("maKhamBenh", maKhamBenh);
        return "redirect:/admin/khambenh/chitiet";
    }


    @PostMapping("/admin/khambenh/chitiet/deleteall")
    public String deleteAllByIds(@RequestParam(value = "selectedIds", required = false) List<Integer> ids,
                                 @RequestParam("maKhamBenh") Integer maKhamBenh,
                                 RedirectAttributes redirectAttributes) {
        if (ids == null || ids.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không có mục nào được chọn để xóa.");
            redirectAttributes.addAttribute("maKhamBenh", maKhamBenh);  // Thêm maKhamBenh vào redirect
            return "redirect:/admin/khambenh/chitiet";
        }

        try {
            chiTietKhamBenhService.deleteAllByIds(ids);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công các mục đã chọn.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa các mục đã chọn.");
        }

        redirectAttributes.addAttribute("maKhamBenh", maKhamBenh);
        return "redirect:/admin/khambenh/chitiet";
    }

    @GetMapping("/users/khambenh/chitiet")
    public String renderListCTKhamBenh(HttpSession session,
                              @RequestParam("maKhamBenh") Integer maKhamBenh,
                              Model model) {

        List<String> pageName = (List<String>) session.getAttribute("pageName");
        if(!pageName.contains("Chi tiết")) {
            pageName.add("Chi tiết");
        }
        session.setAttribute("pageName", pageName);

        List<ChiTietKhamBenhDTO> dsChiTietKhamBenhDTO = chiTietKhamBenhMapper.toChiTietKhamBenhDtoList(
            chiTietKhamBenhService.getDSChiTietKhamBenh(maKhamBenh)
        );

        System.out.println("dsChiTietKhamBenhDTO = " + dsChiTietKhamBenhDTO.size());

        model.addAttribute("dsChiTietKhamBenh", dsChiTietKhamBenhDTO);

        return "users/chitietbenh";
    }

    @GetMapping("/users/khambenh/thongtinbenh")
    public String renderThongTinBenh(HttpSession session,
                              @RequestParam("maChiTietKhamBenh") Integer maChiTietKhamBenh,
                              Model model) {

        List<String> pageName = (List<String>) session.getAttribute("pageName");
        if(!pageName.contains("Thông tin bệnh")) {
            pageName.add("Thông tin bệnh");
        }
        session.setAttribute("pageName", pageName);

        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoidung");

        ChiTietKhamBenh chiTietKhamBenh = chiTietKhamBenhService.findById(maChiTietKhamBenh);

        ThongTinBenhDTO thongTinBenhDTO = chiTietKhamBenhService.getAllThongTinBenh(chiTietKhamBenh);

        model.addAttribute("thongTinBenh", thongTinBenhDTO);
        model.addAttribute("nguoiDung", nguoiDung);

        return "users/thongtinbenh";
    }
}
