package com.nhom27.nhatkykhambenh.controller;

import com.nhom27.nhatkykhambenh.dto.*;
import com.nhom27.nhatkykhambenh.mapper.*;
import com.nhom27.nhatkykhambenh.model.*;
import com.nhom27.nhatkykhambenh.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ThongKeController {

    @Autowired
    private IGiaDinhService giaDinhService;
    @Autowired
    private GiaDinhMapper giaDinhMapper;
    @Autowired
    private IKhamBenhService khamBenhService;
    @Autowired
    private KhamBenhMapper khamBenhMapper;
    @Autowired
    private INguoiDungService nguoiDungService;
    @Autowired
    private NguoiDungMapper nguoiDungMapper;
    @Autowired
    private ITiemChungService tiemChungService;
    @Autowired
    private TiemChungMapper tiemChungMapper;
    @Autowired
    private IChiTietTiemChungService chiTietTiemChungService;
    @Autowired
    private ChiTietTiemChungMapper chiTietTiemChungMapper;
    @Autowired
    private IChiTietBenhService chiTietBenhService;
    @Autowired
    private ChiTietBenhMapper chiTietBenhMapper;
    @Autowired
    private IXetNghiemService xetNghiemService;
    @Autowired
    private XetNghiemMapper xetNghiemMapper;

    @GetMapping("/admin/dashboard")
    public String Dashboard(Model model) {

        List<GiaDinhDTO> giaDinhDTOList = giaDinhMapper.toGiaDinhDtoList(
                giaDinhService.getAll()
        );

        List<KhamBenhDTO> khamBenhDTOList = khamBenhMapper.toKhamBenhDtoList(
                khamBenhService.getAll()
        );

        List<NguoiDungDTO> nguoiDungDTOList = nguoiDungMapper.toNguoiDungDtoList(
                nguoiDungService.getAllNguoiDung()
        );

        List<ChiTietTiemChungDTO> chiTietTiemChungDTOList = chiTietTiemChungMapper.toChiTietTiemChungDtoList(
                chiTietTiemChungService.getAll()
        );
        Map<Boolean, Long> soLuongTiemChung = chiTietTiemChungDTOList.stream()
                .collect(Collectors.groupingBy(ChiTietTiemChungDTO::getTrangThai, Collectors.counting()));
        // Lấy số lượng đã tiêm (true) và chưa tiêm (false)
        long daTiem = soLuongTiemChung.getOrDefault(true, 0L);
        long chuaTiem = soLuongTiemChung.getOrDefault(false, 0L);

        List<ChiTietBenhDTO> chiTietBenhList = chiTietBenhMapper.toChiTietBenhDtoList(
                chiTietBenhService.getAll()
        );
        Map<String, Long> soLuongBenhTheoTen = chiTietBenhList.stream()
                .collect(Collectors.groupingBy(ChiTietBenhDTO::getTenBenh, Collectors.counting()));

        List<Integer> visitsData = getVisitsData(khamBenhDTOList);

        List<XetNghiemDTO> xetNghiemDTOList = xetNghiemMapper.toXetNghiemDtoList(
                xetNghiemService.getAll()
        );
        Map<String, Long> soLuongXetNghiemTheoTen = xetNghiemDTOList.stream()
                .collect(Collectors.groupingBy(XetNghiemDTO::getTenXetNghiem, Collectors.counting()));
        List<Map.Entry<String, Long>> top5XetNghiem = soLuongXetNghiemTheoTen.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))  // Sắp xếp theo số lượng giảm dần
                .limit(5)
                .toList();

        model.addAttribute("dsGiaDinh", giaDinhDTOList);
        model.addAttribute("dsKhamBenh", khamBenhDTOList);
        model.addAttribute("dsNguoiDung", nguoiDungDTOList);
        model.addAttribute("dsCTTiemChung", chiTietTiemChungDTOList);
        model.addAttribute("dsXetNghiem", xetNghiemDTOList);

        model.addAttribute("visitsData", visitsData);
        model.addAttribute("soLuongBenhTheoTen", soLuongBenhTheoTen);
        model.addAttribute("soLuongXetNghiemTheoTen", soLuongXetNghiemTheoTen);
        model.addAttribute("daTiem", daTiem);
        model.addAttribute("chuaTiem", chuaTiem);

        return "admin/thongke";
    }

    @GetMapping("/admin/dashboard/filter")
    public String filterDashboard(
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(required = false) String maGiaDinh,
            Model model) {

        GiaDinh giaDinh = null;
        if (maGiaDinh != null && !maGiaDinh.isEmpty()) {
            giaDinh = giaDinhMapper.toGiaDinh(giaDinhService.findById(Integer.parseInt(maGiaDinh)));
        }

        List<GiaDinhDTO> giaDinhDTOList = giaDinhMapper.toGiaDinhDtoList(giaDinhService.getAll());
        List<KhamBenhDTO> khamBenhDTOList = khamBenhMapper.toKhamBenhDtoList(
                khamBenhService.filterKhamBenh(dateFrom, dateTo, maGiaDinh)
        );
        List<Integer> visitsData = getVisitsData(khamBenhDTOList);

        List<NguoiDungDTO> nguoiDungDTOList = nguoiDungMapper.toNguoiDungDtoList(
                nguoiDungService.getDsNguoiDungByGiaDinh(giaDinh)
        );

        List<ChiTietTiemChungDTO> chiTietTiemChungDTOList = chiTietTiemChungMapper.toChiTietTiemChungDtoList(
                chiTietTiemChungService.filterChiTietTiemChung(dateFrom, dateTo, maGiaDinh)
        );
        Map<Boolean, Long> soLuongTiemChung = chiTietTiemChungDTOList.stream()
                .collect(Collectors.groupingBy(ChiTietTiemChungDTO::getTrangThai, Collectors.counting()));
        // Lấy số lượng đã tiêm (true) và chưa tiêm (false)
        long daTiem = soLuongTiemChung.getOrDefault(true, 0L);
        long chuaTiem = soLuongTiemChung.getOrDefault(false, 0L);

        List<XetNghiemDTO> xetNghiemDTOList = xetNghiemMapper.toXetNghiemDtoList(
                xetNghiemService.filterXetNghiem(dateFrom, dateTo, maGiaDinh)
        );
        Map<String, Long> soLuongXetNghiemTheoTen = xetNghiemDTOList.stream()
                .collect(Collectors.groupingBy(XetNghiemDTO::getTenXetNghiem, Collectors.counting()));
        List<Map.Entry<String, Long>> top5XetNghiem = soLuongXetNghiemTheoTen.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))  // Sắp xếp theo số lượng giảm dần
                .limit(5)
                .toList();

        List<ChiTietBenhDTO> chiTietBenhList = chiTietBenhMapper.toChiTietBenhDtoList(
                chiTietBenhService.filterChiTietBenh(maGiaDinh)
        );
        Map<String, Long> soLuongBenhTheoTen = chiTietBenhList.stream()
                .collect(Collectors.groupingBy(ChiTietBenhDTO::getTenBenh, Collectors.counting()));


        model.addAttribute("dsGiaDinh", giaDinhDTOList);
        model.addAttribute("dsKhamBenh", khamBenhDTOList);
        model.addAttribute("dsNguoiDung", nguoiDungDTOList);
        model.addAttribute("dsCTTiemChung", chiTietTiemChungDTOList);
        model.addAttribute("dsXetNghiem", xetNghiemDTOList);

        // Chart
        model.addAttribute("visitsData", visitsData);
        model.addAttribute("soLuongBenhTheoTen", soLuongBenhTheoTen);
        model.addAttribute("soLuongXetNghiemTheoTen", soLuongXetNghiemTheoTen);
        model.addAttribute("daTiem", daTiem);
        model.addAttribute("chuaTiem", chuaTiem);

        return "admin/thongke";
    }

    private List<Integer> getVisitsData(List<KhamBenhDTO> khamBenhDTOList) {
        // Theo tuan
        List<Integer> visitsData = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            visitsData.add(0);
        }

        for (KhamBenhDTO khamBenh : khamBenhDTOList) {
            int weekDay = khamBenh.getNgayKham().getDayOfWeek().getValue() - 1;
            visitsData.set(weekDay, visitsData.get(weekDay) + 1);
        }

        return visitsData;
    }
}
