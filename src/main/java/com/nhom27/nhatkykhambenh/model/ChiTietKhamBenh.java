package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietKhamBenh")
public class ChiTietKhamBenh {
    public static final String OBJ_NAME = "ChiTietKhamBenh";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChiTietKhamBenh")
    private Integer maChiTietKhamBenh;

    @Column(name = "KhoaKham", length = 250)
    private String khoaKham;

    @Column(name = "ThoiGianVaoKham")
    private LocalDateTime thoiGianVaoKham;

    @Column(name = "BacSiKham", length = 250)
    private String bacSiKham;

    @Column(name = "ChiDinh", length = 250)
    private String chiDinh;

    @Column(name = "ChuanDoan", length = 250)
    private String chuanDoan;

    @Column(name = "NhomMau", length = 250)
    private String nhomMau;

    @Column(name = "TinhTrang", length = 250)
    private String tinhTrang;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaKhamBenh", nullable = true)
    private KhamBenh khamBenh;

    @OneToMany(mappedBy = "chiTietKhamBenh",cascade = CascadeType.ALL)
    private Set<HinhAnh> danhSachHinhAnh = new HashSet<>();

    @OneToMany(mappedBy = "chiTietKhamBenh",cascade = CascadeType.ALL)
    private Set<XetNghiem> danhSachXetNghiem = new HashSet<>();
}
