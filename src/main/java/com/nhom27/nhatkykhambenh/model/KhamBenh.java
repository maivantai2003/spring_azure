package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KhamBenh")
public class KhamBenh {
    public static final String OBJ_NAME = "KhamBenh";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKhamBenh")
    private Integer maKhamBenh;

    @Column(name = "BenhVien", length = 250)
    private String benhVien;

    @Column(name = "NgayKham")
    private LocalDateTime ngayKham;

    @Column(name = "TrangThai")
    private Boolean trangThai = true;

    @ManyToOne
    @JoinColumn(name = "MaNguoiDung", referencedColumnName = "MaNguoiDung", nullable = true)
    private NguoiDung nguoiDung;

    @OneToMany(mappedBy = "khamBenh",cascade = CascadeType.ALL)
    private Set<ChiTietKhamBenh> danhSachChiTietKhamBenh = new HashSet<>();

    @OneToMany(mappedBy = "khamBenh", fetch = FetchType.LAZY)
    private Set<LichHenKham> danhSachLichHenKham = new HashSet<>();
}
