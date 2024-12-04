package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GiaDinh")
public class GiaDinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGiaDinh")
    private Integer maGiaDinh;

    @Column(name = "SoLuong")
    private Integer soLuong=0;

    @Column(name = "TrangThai")
    private Boolean trangThai=true;

    @OneToOne(mappedBy = "giaDinh", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "giaDinh", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<NguoiDung> danhSachNguoiDung = new HashSet<>();

}
