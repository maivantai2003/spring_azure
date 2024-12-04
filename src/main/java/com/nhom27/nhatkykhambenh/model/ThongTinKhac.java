package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ThongTinKhac")
public class ThongTinKhac {
    @Id
    @Column(name = "MaTheBHYT")
    private String maTheBHYT;

    @Column(name = "ThoiGianBatDau")
    private Timestamp thoiGianBatDau;

    @Column(name = "QuocTich", length = 250)
    private String quocTich;

    @Column(name = "DanToc", length = 250)
    private String danToc;

    @Column(name = "NgheNghiep", length = 250)
    private String ngheNghiep;

    @Column(name = "TonGiao", length = 250)
    private String tonGiao;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNguoiDung", referencedColumnName = "MaNguoiDung")
    private NguoiDung nguoiDung;
}
