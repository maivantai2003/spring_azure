package com.nhom27.nhatkykhambenh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichHenKhamDTO {
    private Integer maHenKham;

    private Timestamp thoiGianHen;

    private Boolean trangThai;

    private KhamBenhDTO khamBenh;
}
