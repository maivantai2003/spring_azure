package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiSo")
public class ChiSo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChiSo")
    private Integer maChiSo;

    @Column(name = "LoaiChiSo", length = 250)
    private String loaiChiSo;

    @Column(name = "TenChiSo")
    private String tenChiSo;

    @Column(name = "DonVi")
    private String donVi;

    @Column(name = "TrangThai")
    private Boolean trangThai = true;

}
