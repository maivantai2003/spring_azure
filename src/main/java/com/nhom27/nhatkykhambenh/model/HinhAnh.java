package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HinhAnh")
public class HinhAnh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHinhAnh")
    private Integer maHinhAnh;

    @Column(name = "HinhAnh", length = 250)
    private String hinhAnh;

    @Column(name = "ChuanDoan")
    private String chuanDoan;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaChiTietKhamBenh", referencedColumnName="MaChiTietKhamBenh", updatable = false)
    private ChiTietKhamBenh chiTietKhamBenh;
}
