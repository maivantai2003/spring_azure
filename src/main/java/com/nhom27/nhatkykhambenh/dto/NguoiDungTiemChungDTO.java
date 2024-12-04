package com.nhom27.nhatkykhambenh.dto;
import com.nhom27.nhatkykhambenh.model.NguoiDungTiemChungId;
import com.nhom27.nhatkykhambenh.repository.INguoiDungTiemChung;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NguoiDungTiemChungDTO {
    private Integer maNguoiDungTiemChung;
    private String tenVaccin;
    private Boolean trangThai;
    private NguoiDungDTO nguoiDung;
}