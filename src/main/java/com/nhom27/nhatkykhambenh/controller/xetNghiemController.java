package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.XetNghiemDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.mapper.XetNghiemMapper;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietKhamBenhService;
import com.nhom27.nhatkykhambenh.service.interfaces.IXetNghiemService;
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
public class xetNghiemController {

    @Autowired
    private IXetNghiemService xetNghiemService;

    @Autowired
    private XetNghiemMapper xetNghiemMapper;

    @GetMapping("/admin/khambenh/chitiet/xetnghiem")
    public String GetListXetNghiem(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(defaultValue = "") String query,
                                   @RequestParam Integer maChiTietKhamBenh) {
        Pageable pageable = PageRequest.of(page, size);
        Page<XetNghiem> xetNghiemPage = xetNghiemService.getDSXetNghiem(pageable, query, maChiTietKhamBenh);
        List<XetNghiemDTO> xetNghiemDTOList = xetNghiemMapper.toXetNghiemDtoList(xetNghiemPage.getContent());

        System.out.println("size XetNghiem = " + xetNghiemDTOList.size());

        model.addAttribute("dsXetNghiem", xetNghiemDTOList);
        model.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", xetNghiemPage.getTotalPages());
        model.addAttribute("totalItems", xetNghiemPage.getTotalElements());

        model.addAttribute("query", query);

        int startItem = page * size + 1;
        int endItem = Math.min(startItem + size - 1, (int) xetNghiemPage.getTotalElements());

        model.addAttribute("startItem", startItem);
        model.addAttribute("endItem", endItem);
        model.addAttribute("currentCount", endItem - startItem + 1);

        return "admin/khambenh/listXetNghiem";
    }

    @GetMapping("/admin/khambenh/chitiet/xetnghiem/add")
    public String addXetNghiemForm(Model model, @RequestParam Integer maChiTietKhamBenh) {
        XetNghiemDTO xetNghiemDTO = new XetNghiemDTO();
        xetNghiemDTO.setMaChiTietKhamBenh(maChiTietKhamBenh);

        model.addAttribute("xetnghiem", xetNghiemDTO);
        model.addAttribute("maChiTietKhamBenh2", maChiTietKhamBenh);

        return "admin/khambenh/addXetNghiem";
    }


    @GetMapping("/admin/khambenh/chitiet/xetnghiem/update")
    public String updateXetNghiemForm(@RequestParam Integer maXetNghiem,
                                      @RequestParam Integer maChiTietKhamBenh,
                                      Model model) {
        XetNghiem xetNghiem = xetNghiemService.findById(maXetNghiem);
        XetNghiemDTO xetNghiemDTO = xetNghiemMapper.toXetNghiemDTO(xetNghiem);
        xetNghiemDTO.setMaChiTietKhamBenh(maChiTietKhamBenh);

        model.addAttribute("xetnghiem", xetNghiemDTO);
        model.addAttribute("maChiTietKhamBenh2", maChiTietKhamBenh);
        return "admin/khambenh/addXetNghiem";
    }

    @PostMapping("/admin/khambenh/chitiet/xetnghiem/save")
    public String saveXetNghiem(@ModelAttribute("xetnghiem") XetNghiemDTO xetNghiemDTO,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam(required = false) Integer maChiTietKhamBenh,
                                @RequestParam(required = false) Integer maXetNghiem,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "admin/khambenh/addXetNghiem";
        }

        try {
            if (maXetNghiem != null) {
                xetNghiemDTO.setMaXetNghiem(maXetNghiem);
            }
            XetNghiem xetNghiem = xetNghiemMapper.toXetNghiem(xetNghiemDTO);
            xetNghiemService.saveXetNghiem(xetNghiem, maChiTietKhamBenh);

            redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
            return "redirect:/admin/khambenh/chitiet/xetnghiem";
        } catch (SaveDataException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/khambenh/addXetNghiem";
        }
    }


//    @PostMapping("/admin/khambenh/chitiet/xetnghiem/delete")
//    public String deleteXetNghiem(@RequestParam("maXetNghiem") Integer maXetNghiem,
//                                  @RequestParam("maChiTietKhamBenh") Integer maChiTietKhamBenh,
//                                  RedirectAttributes redirectAttributes) {
//        try {
//            xetNghiemService.deleteById(maXetNghiem);
//            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Lỗi!! Xóa thông tin Xét Nghiệm thất bại");
//        }
//        redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
//        return "redirect:/admin/khambenh/chitiet/xetnghiem";
//    }

//    @PostMapping("/admin/khambenh/chitiet/xetnghiem/deleteall")
//    public String deleteAllByIds(@RequestParam(value = "selectedIds", required = false) List<Integer> ids,
//                                 @RequestParam("maChiTietKhamBenh") Integer maChiTietKhamBenh,
//                                 RedirectAttributes redirectAttributes) {
//        if (ids == null || ids.isEmpty()) {
//            redirectAttributes.addFlashAttribute("error", "Không có mục nào được chọn để xóa.");
//            redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);  // Thêm maKhamBenh vào redirect
//            return "redirect:/admin/khambenh/chitiet/xetnghiem";
//        }
//
//        try {
//            xetNghiemService.deleteAllByIds(ids);
//            redirectAttributes.addFlashAttribute("success", "Xóa thành công các mục đã chọn.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa các mục đã chọn.");
//        }
//
//        redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
//        return "redirect:/admin/khambenh/chitiet/xetnghiem";
//    }

    @PostMapping("/admin/khambenh/chitiet/xetnghiem/delete")
    public String deleteXetNghiem(@RequestParam("maXetNghiem") Integer maXetNghiem,
                                  @RequestParam("maChiTietKhamBenh") Integer maChiTietKhamBenh,
                                  RedirectAttributes redirectAttributes) {
        try {
            xetNghiemService.deleteById(maXetNghiem);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi!! Xóa thông tin Xét Nghiệm thất bại");
        }
        redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
        return "redirect:/admin/khambenh/chitiet/xetnghiem";
    }

    @PostMapping("/admin/khambenh/chitiet/xetnghiem/deleteall")
    public String deleteAllByIds(@RequestParam(value = "selectedIds", required = false) List<Integer> ids,
                                 @RequestParam("maChiTietKhamBenh") Integer maChiTietKhamBenh,
                                 RedirectAttributes redirectAttributes) {
        if (ids == null || ids.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không có mục nào được chọn để xóa.");
            redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
            return "redirect:/admin/khambenh/chitiet/xetnghiem";
        }

        try {
            xetNghiemService.deleteAllByIds(ids);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công các mục đã chọn.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa các mục đã chọn.");
        }

        redirectAttributes.addAttribute("maChiTietKhamBenh", maChiTietKhamBenh);
        return "redirect:/admin/khambenh/chitiet/xetnghiem";
    }

}
