package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.TiemChungDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietTiemChungService;
import com.nhom27.nhatkykhambenh.service.interfaces.INguoiDungService;
import com.nhom27.nhatkykhambenh.service.interfaces.ITiemChungService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TiemChungController {

    @Autowired
    private ITiemChungService tiemChungService;

    @Autowired
    private IChiTietTiemChungService chiTietTiemChungService;

    @Autowired
    private INguoiDungService nguoiDungService;

    @GetMapping("/admin/tiemchung")
    public String GetListTiemChung(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(defaultValue = "") String query) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TiemChungDTO> tiemChungPage = tiemChungService.getDSTiemChung(pageable, query);

        model.addAttribute("dsTiemChung", tiemChungPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", tiemChungPage.getTotalPages());
        model.addAttribute("totalItems", tiemChungPage.getTotalElements());
        model.addAttribute("query", query);

        int startItem = page * size + 1;
        int endItem = Math.min(startItem + size - 1, (int) tiemChungPage.getTotalElements());

        model.addAttribute("startItem", startItem);
        model.addAttribute("endItem", endItem);
        model.addAttribute("currentCount", endItem - startItem + 1);

        return "admin/tiemchung/listTiemChung";
    }

    @GetMapping("/admin/tiemchung/add")
    public String addTiemChungForm(Model model) {
        TiemChungDTO tiemChungDTO = new TiemChungDTO();
        model.addAttribute("tiemchung", tiemChungDTO);
        return "admin/tiemchung/addTiemChung";
    }

    @GetMapping("/admin/tiemchung/update")
    public String updateTiemChungForm(@RequestParam("id") Integer id, Model model) {
        TiemChungDTO tiemChungDTO = tiemChungService.findById(id);
        model.addAttribute("tiemchung", tiemChungDTO);
        return "admin/tiemchung/addTiemChung";
    }

    @PostMapping("/admin/tiemchung/save")
    public String saveTiemChung(@ModelAttribute("tiemchung") TiemChungDTO tiemchungDTO,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/tiemchung/addTiemChung";
        }
        try {
            tiemChungService.saveTiemChung(tiemchungDTO);
            return "redirect:/admin/tiemchung";
        } catch (SaveDataException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/tiemchung/addTiemChung";
        }
    }

    @PostMapping("/admin/tiemchung/delete")
    public String deleteTiemChung(@RequestParam("maTiemChung") Integer maTiemChung, RedirectAttributes redirectAttributes) {
        try {
            tiemChungService.deleteById(maTiemChung);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi!! Xóa thông tin tiêm chủng thất bại");
        }
        return "redirect:/admin/tiemchung";
    }

    @PostMapping("/admin/tiemchung/deleteall")
    public String deleteAllByIds(@RequestParam("selectedIds") List<Integer> ids, RedirectAttributes redirectAttributes) {
        try {
            tiemChungService.deleteAllByIds(ids);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công các mục đã chọn.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa.");
        }
        return "redirect:/admin/tiemchung";
    }
    @PostMapping("/admin/tiemchung/deleteMutil")
    public String deleteMutil(@RequestParam("test") Integer[] str) {

        for (Integer s : str) {
            tiemChungService.deleteById(s);
        }
        return "redirect:/admin/tiemchung";
    }

}
