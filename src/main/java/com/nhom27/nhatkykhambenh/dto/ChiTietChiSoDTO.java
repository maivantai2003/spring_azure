package com.nhom27.nhatkykhambenh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nhom27.nhatkykhambenh.model.ChiSo;
import com.nhom27.nhatkykhambenh.model.ChiTietChiSo;
import com.nhom27.nhatkykhambenh.model.TongQuan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChiTietChiSoDTO {
    private Integer maChiSo;

    private Integer maTongQuan;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date thoiGianDo;

    private String ketQuaDo;

}
