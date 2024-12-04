package com.nhom27.nhatkykhambenh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nhom27.nhatkykhambenh.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KhamBenhDTO {
    private Integer maKhamBenh;
    private String benhVien;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngayKham;
    private Boolean trangThai;
    private NguoiDung nguoiDung;
    private Set<ChiTietKhamBenh> danhSachChiTietKhamBenh;
    private Set<LichHenKham> danhSachLichHenKham;
    private DonThuoc donThuoc;
}
