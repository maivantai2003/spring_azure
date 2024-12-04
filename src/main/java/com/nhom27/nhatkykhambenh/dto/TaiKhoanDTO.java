package com.nhom27.nhatkykhambenh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaiKhoanDTO {
    private Integer maNguoiDung;

    private String matKhau;

    private String soDienThoai;

    private Boolean trangThai;

    private Integer maGiaDinh;

    private List<Integer> danhSachRoleIds;

    private NguoiDungDTO nguoiDung;
}
