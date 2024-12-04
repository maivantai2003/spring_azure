package com.nhom27.nhatkykhambenh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XetNghiemDTO {
    private Integer maXetNghiem;
    private Integer maChiTietKhamBenh;
    private String ketQuaXetNghiem;
    private String tenXetNghiem;
    private Boolean trangThai;
}
