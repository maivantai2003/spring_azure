package com.nhom27.nhatkykhambenh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import com.nhom27.nhatkykhambenh.model.DonThuoc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChiTietDonThuocDTO {
    private DonThuoc donThuoc;

    private ChiTietKhamBenh chiTietKhamBenh;

    private Integer soLuongThuoc;

    private Integer lieuLuong;

    private Boolean trangThai;

}
