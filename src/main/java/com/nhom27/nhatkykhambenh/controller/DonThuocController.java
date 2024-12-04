package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.DonThuocDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.mapper.DonThuocMapper;
import com.nhom27.nhatkykhambenh.model.DonThuoc;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietKhamBenhService;
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
public class DonThuocController {

    @Autowired
    private IDonThuocService donThuocService;

    @Autowired
    private DonThuocMapper donThuocMapper;

    @Autowired
    private IChiTietKhamBenhService chiTietKhamBenhService;

    @GetMapping("/admin/donthuoc")
    public String GetListDonThuoc(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(defaultValue = "") String query) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DonThuoc> donThuocPage = donThuocService.getDSDonThuoc(pageable, query);
        List<DonThuocDTO> donThuocDTOList = donThuocMapper.toDonThuocDtoList(donThuocPage.getContent());

        model.addAttribute("dsDonThuoc", donThuocDTOList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", donThuocPage.getTotalPages());
        model.addAttribute("totalItems", donThuocPage.getTotalElements());

        model.addAttribute("query", query);

        int startItem = page * size + 1;
        int endItem = Math.min(startItem + size - 1, (int) donThuocPage.getTotalElements());

        model.addAttribute("startItem", startItem);
        model.addAttribute("endItem", endItem);
        model.addAttribute("currentCount", endItem - startItem + 1);

        return "admin/donthuoc/listDonThuoc";
    }

    @GetMapping("/admin/donthuoc/add")
    public String addDonThuocForm(Model model) {
        model.addAttribute("donthuoc", new DonThuocDTO());

        return "admin/donthuoc/addDonThuoc";
    }


    @GetMapping("/admin/donthuoc/update")
    public String updateDonThuocForm(@RequestParam Integer maDonThuoc, Model model) {
        DonThuoc donThuoc = donThuocService.findById(maDonThuoc);
        DonThuocDTO donThuocDTO = donThuocMapper.toDonThuocDTO(donThuoc);

        model.addAttribute("donthuoc", donThuocDTO);
        return "admin/donthuoc/addDonThuoc";
    }

    @PostMapping("/admin/donthuoc/save")
    public String saveDonThuoc(@ModelAttribute("donthuoc") DonThuocDTO donThuocDTO,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/donthuoc/addDonThuoc";
        }
        try {
            DonThuoc donThuoc = donThuocMapper.toDonThuoc(donThuocDTO);
            donThuocService.saveDonThuoc(donThuoc);
            return "redirect:/admin/donthuoc";
        } catch (SaveDataException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/donthuoc/addDonThuoc";
        }
    }

    @PostMapping("/admin/donthuoc/delete")
    public String deleteDonThuoc(@RequestParam("maDonThuoc") Integer maDonThuoc,
                                 RedirectAttributes redirectAttributes) {
        try {
            donThuocService.deleteById(maDonThuoc);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi!! Xóa thông tin Đơn Thuốc thất bại");
        }
        return "redirect:/admin/donthuoc";
    }

    @PostMapping("/admin/donthuoc/deleteall")
    public String deleteAllByIds(@RequestParam(value = "selectedIds", required = false) List<Integer> ids,
                                RedirectAttributes redirectAttributes) {
        if (ids == null || ids.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không có mục nào được chọn để xóa.");
            return "redirect:/admin/donthuoc";
        }
        try {
            donThuocService.deleteAllByIds(ids);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công các mục đã chọn.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa các mục đã chọn.");
        }
        return "redirect:/admin/donthuoc";
    }
}
