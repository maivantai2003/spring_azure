package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan {
    @Id
    @Column(name = "MaNguoiDung")
    private Integer maNguoiDung;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "TrangThai")
    private Boolean trangThai = true;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "MaGiaDinh", referencedColumnName = "MaGiaDinh")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GiaDinh giaDinh;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNguoiDung", referencedColumnName = "MaNguoiDung")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private NguoiDung nguoiDung;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "TaiKhoan_Roles",
            joinColumns = {@JoinColumn(name = "maNguoiDung", referencedColumnName = "maNguoiDung")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    @ToString.Exclude
    private List<Role> danhSachRole = new ArrayList<>();
}