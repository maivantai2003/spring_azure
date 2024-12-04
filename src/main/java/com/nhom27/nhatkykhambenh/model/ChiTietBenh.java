package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietBenh")
public class ChiTietBenh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChiTietBenh")
    private Integer maChiTietBenh;

    @Column(name = "TenBenh", length = 250)
    private String tenBenh;

    @Column(name = "TrangThaiBenhHienTai")
    private Boolean trangThaiBenhHienTai;

    @Column(name = "TrangThai")
    private Boolean trangThai=true;

    @ManyToOne
    @JoinColumn(name = "MaTongQuan", referencedColumnName = "MaTongQuan")
    private TongQuan tongQuan;
}