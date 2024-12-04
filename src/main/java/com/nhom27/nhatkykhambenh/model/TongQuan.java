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
@Table(name = "TongQuan")
public class TongQuan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTongQuan")
    private Integer maTongQuan;

    @Column(name = "DuongHuyet", length = 250)
    private String duongHuyet = "0";

    @Column(name = "NhipTim", length = 250)
    private String nhipTim= "0";

    @Column(name = "HuyetAp", length = 250)
    private String huyetAp= "0";

    @Column(name = "NhietDo", length = 250)
    private String nhietDo= "0";

    @Column(name = "ChieuCao", length = 250)
    private String chieuCao= "0";

    @Column(name = "CanNang", length = 250)
    private String canNang= "0";

    @Column(name = "ChiSoBMI", length = 250)
    private String chiSoBMI= "0";

    @Column(name = "NhomMau", length = 250)
    private String nhomMau= "0";

    @Column(name = "TrangThai")
    private Boolean trangThai = true;

    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNguoiDung", referencedColumnName = "MaNguoiDung")
    private NguoiDung nguoiDung;

    @OneToMany(mappedBy = "tongQuan", fetch = FetchType.LAZY)
    private Set<ChiTietBenh> danhSachChiTietBenh = new HashSet<>();

}
