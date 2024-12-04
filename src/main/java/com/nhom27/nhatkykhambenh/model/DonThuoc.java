package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DonThuoc")
public class DonThuoc {

    public static final String OBJ_NAME = "DonThuoc";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDonThuoc")
    private Integer maDonThuoc;

    @Column(name = "TenThuoc")
    private String tenThuoc;

    @Column(name = "HamLuong")
    private Integer hamLuong;

    @Column(name = "DonViTinh")
    private String donViTinh;

    @Column(name = "TrangThai")
    private Boolean trangThai;
}
