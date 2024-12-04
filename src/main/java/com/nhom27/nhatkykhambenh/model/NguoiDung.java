package com.nhom27.nhatkykhambenh.model;

import com.nhom27.nhatkykhambenh.enums.MoiQuanHe;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "NguoiDung")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNguoiDung")
    private Integer maNguoiDung;

    @Column(name = "HinhAnh")
    private String hinhAnh;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "CCCD")
    private String cccd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NgayThangNamSinh")
    private LocalDate ngayThangNamSinh;

    @Column(name = "GioiTinh")
    private String gioiTinh;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name="TenNguoiDung")
    private String tenNguoiDung;

    @Column(name = "Email")
    private String email;

    @Column(name = "MoiQuanHe")
    private MoiQuanHe moiQuanHe;

    @Column(name = "TrangThai")
    private Boolean trangThai = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaGiaDinh")
    private GiaDinh giaDinh;

    @OneToOne(mappedBy = "nguoiDung", fetch = FetchType.LAZY)
    private ThongTinKhac thongTinKhac;

    @OneToOne(mappedBy = "nguoiDung")
    private TongQuan tongQuan;

    @OneToMany(mappedBy = "nguoiDung", fetch = FetchType.LAZY)
    private Set<NguoiDungTiemChung> danhSachNguoiDungTiemChung = new HashSet<>();

    @OneToOne(mappedBy = "nguoiDung", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "nguoiDung", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PasswordResetToken> passwordResetTokens;
}
