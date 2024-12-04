package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class NguoiDungTiemChungId implements Serializable {
    private Integer maNguoiDung;
    private Integer maLichHenTiemChung;
}