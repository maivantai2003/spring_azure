package com.nhom27.nhatkykhambenh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nhom27.nhatkykhambenh.model.ChiTietDonThuoc;
import com.nhom27.nhatkykhambenh.model.HinhAnh;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThongTinBenhDTO {
    private Integer maChiTietKhamBenh;

    private String bacSi;

    private String chuanDoan;

    private String chiDinh;

    private String khoaKham;

    private String benhVien;

    private String nhomMau;

    private String tinhTrang;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngayKhamChiTietKhamBenh;

    private List<XetNghiem> dsXetNghiem;

    private List<HinhAnh> dsHinhAnh;

    private List<ChiTietDonThuoc> dsChiTietDonThuoc;

    public ThongTinBenhDTO(Integer maChiTietKhamBenh, String bacSiKham, String chiDinh, String khoaKham, String benhVien, String nhomMau, String tinhTrang, LocalDateTime ngayKhamChiTietKhamBenh, List<XetNghiem> dsXetNghiem, List<HinhAnh> dsHinhAnh, List<ChiTietDonThuoc> dsChiTietDonThuoc) {
    }
}
