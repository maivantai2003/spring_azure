package com.nhom27.nhatkykhambenh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LichHenTiemChungDTO {
    private Integer maLichHenTiemChung;
    private String noiTiemChung;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime ngayHenTiem;
    private Set<NguoiDungTiemChungDTO> nguoiDungTiemChungList;
    private String trangThai;
}
