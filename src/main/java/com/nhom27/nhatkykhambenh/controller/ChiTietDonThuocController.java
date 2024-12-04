package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.ChiTietDonThuocDTO;
import com.nhom27.nhatkykhambenh.dto.DonThuocDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.mapper.ChiTietDonThuocMapper;
import com.nhom27.nhatkykhambenh.mapper.DonThuocMapper;
import com.nhom27.nhatkykhambenh.model.ChiTietDonThuoc;
import com.nhom27.nhatkykhambenh.model.DonThuoc;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietDonThuocService;
import com.nhom27.nhatkykhambenh.service.interfaces.IDonThuocService;
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
public class ChiTietDonThuocController {

    @Autowired
    private IChiTietDonThuocService chiTietDonThuocService;

    @Autowired
    private ChiTietDonThuocMapper chiTietDonThuocMapper;

    @Autowired
    private IDonThuocService donThuocService;

    @Autowired
    private DonThuocMapper donThuocMapper;

    @GetMapping("/admin/khambenh/chitiet/chitietdonthuoc")
    public String GetListChiTietDonThuoc(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @RequestParam(defaultValue = "") String query,
                                  @RequestParam Integer maChiTietKhamBenh) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ChiTietDonThuoc> chiTietDonThuocPage = chiTietDonThuocService.getDSChiTietDonThuoc(pageable, query, maChiTietKhamBenh);
        List<ChiTietDonThuocDTO> chiTietDonThuocDTOList = chiTietDonThuocMapper.toChiTietDonThuocDtoList(chiTietDonThuocPage.getContent());

        model.addAttribute("dsChiTietDonThuoc", chiTietDonThuocDTOList);
        model.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", chiTietDonThuocPage.getTotalPages());
        model.addAttribute("totalItems", chiTietDonThuocPage.getTotalElements());

        model.addAttribute("query", query);

        int startItem = page * size + 1;
        int endItem = Math.min(startItem + size - 1, (int) chiTietDonThuocPage.getTotalElements());

        model.addAttribute("startItem", startItem);
        model.addAttribute("endItem", endItem);
        model.addAttribute("currentCount", endItem - startItem + 1);

        return "admin/khambenh/listChiTietDonThuoc";
    }

    @GetMapping("/admin/khambenh/chitiet/chitietdonthuoc/add")
    public String addChTietDonThuocForm(Model model, @RequestParam Integer maChiTietKhamBenh) {
        ChiTietDonThuocDTO chiTietDonThuocDTO = new ChiTietDonThuocDTO();
        List<DonThuocDTO> dsDonThuoc = donThuocMapper.toDonThuocDtoList(donThuocService.getAllDonThuoc());

        model.addAttribute("dsDonThuoc", dsDonThuoc);
        model.addAttribute("chitietdonthuoc", chiTietDonThuocDTO);
        model.addAttribute("maChiTietKhamBenh2", maChiTietKhamBenh);

        return "admin/khambenh/addChiTietDonThuoc";
    }

    @GetMapping("/admin/khambenh/chitiet/chitietdonthuoc/update")
    public String updateChiTietDonThuocForm(@RequestParam Integer maChiTietKhamBenh,
                                            @RequestParam Integer maDonThuoc,
                                            Model model) {

        ChiTietDonThuoc chiTietDonThuoc = chiTietDonThuocService.findById(maDonThuoc, maChiTietKhamBenh);
        ChiTietDonThuocDTO chiTietDonThuocDTO = chiTietDonThuocMapper.toChiTietDonThuocDTO(chiTietDonThuoc);
        List<DonThuocDTO> dsDonThuoc = donThuocMapper.toDonThuocDtoList(donThuocService.getAllDonThuoc());

        model.addAttribute("chitietdonthuoc", chiTietDonThuocDTO);
        model.addAttribute("maChiTietKhamBenh2", maChiTietKhamBenh);
        model.addAttribute("dsDonThuoc", dsDonThuoc);
        model.addAttribute("maDonThuoc2", maDonThuoc);
        return "admin/khambenh/addChiTietDonThuoc";
    }

    @PostMapping("/admin/khambenh/chitiet/chitietdonthuoc/save")
    public String saveChiTietDonThuoc(@ModelAttribute("chitietdonthuoc") ChiTietDonThuocDTO chiTietDonThuocDTO,
                                      BindingResult bindingResult,
                                      Model model,
                                      @RequestParam(required = false) Integer maDonThuoc,
                                      @RequestParam(required = false) Integer maChiTietKhamBenh,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
            return "redirect:/admin/khambenh/chitiet/chitietdonthuoc/add";
        }
        try {
            ChiTietDonThuoc chiTietDonThuoc = chiTietDonThuocMapper.toChiTietDonThuoc(chiTietDonThuocDTO);
            chiTietDonThuocService.saveChiTietDonThuoc(chiTietDonThuoc, maDonThuoc, maChiTietKhamBenh);

            redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
            return "redirect:/admin/khambenh/chitiet/chitietdonthuoc";
        } catch (SaveDataException e) {
            model.addAttribute("error", e.getMessage());
            redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
            return "redirect:/admin/khambenh/chitiet/chitietdonthuoc/add";
        }
    }

    @PostMapping("/admin/khambenh/chitiet/chitietdonthuoc/delete")
    public String deleteChiTietDonThuoc(@RequestParam("maChiTietKhamBenh") Integer maChiTietKhamBenh,
                                        @RequestParam("maDonThuoc") Integer maDonThuoc,
                                        RedirectAttributes redirectAttributes) {
        try {
            chiTietDonThuocService.deleteById(maDonThuoc, maChiTietKhamBenh);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi!! Xóa thông tin Loại Đơn Thuốc thất bại");
        }
        redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
        return "redirect:/admin/khambenh/chitiet/chitietdonthuoc";
    }

    @PostMapping("/admin/khambenh/chitiet/chitietdonthuoc/deleteall")
    public String deleteAllByIds(@RequestParam(value = "selectedIds", required = false) List<Integer> ids,
                                 @RequestParam("maChiTietKhamBenh") Integer maChiTietKhamBenh,
                                 RedirectAttributes redirectAttributes) {
        if (ids == null || ids.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không có mục nào được chọn để xóa.");
            redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
            return "redirect:/admin/khambenh/chitiet/chitietdonthuoc";
        }

        try {
            chiTietDonThuocService.deleteAllByIds(maChiTietKhamBenh, ids);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công các mục đã chọn.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa các mục đã chọn.");
        }

        redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
        return "redirect:/admin/khambenh/chitiet/chitietdonthuoc";
    }
}
