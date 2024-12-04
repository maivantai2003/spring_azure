package com.nhom27.nhatkykhambenh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TongQuanDTO {
    private Integer maTongQuan;

    private String duongHuyet;

    private String nhipTim;

    private String huyetAp;

    private String nhietDo;

    private String chieuCao;

    private String canNang;

    private String chiSoBMI;

    private String nhomMau;

    private Boolean trangThai;
}
