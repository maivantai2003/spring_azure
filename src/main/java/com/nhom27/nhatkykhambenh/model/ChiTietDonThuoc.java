package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietDonThuoc")
@IdClass(ChiTietDonThuoc.ChiTietDonThuocId.class)
public class ChiTietDonThuoc {
    public static final String OBJ_NAME = "ChiTietDonThuoc";

    @Id
    @Column(name = "MaChiTietKhamBenh")
    private Integer maChiTietKhamBenh;

    @Id
    @Column(name = "MaDonThuoc")
    private Integer maDonThuoc;

    @Column(name = "SoLuongThuoc")
    private Integer soLuongThuoc;

    @Column(name = "LieuLuong")
    private Integer lieuLuong;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    public ChiTietDonThuoc.ChiTietDonThuocId getId() {
        return new ChiTietDonThuoc.ChiTietDonThuocId(maChiTietKhamBenh, maDonThuoc);
    }

    @ManyToOne
    @JoinColumn(name = "MaChiTietKhamBenh", referencedColumnName = "MaChiTietKhamBenh", insertable = false, updatable = false)
    private ChiTietKhamBenh chiTietKhamBenh;

    @ManyToOne
    @JoinColumn(name = "MaDonThuoc", referencedColumnName = "MaDonThuoc", insertable = false, updatable = false)
    private DonThuoc donThuoc;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChiTietDonThuocId implements Serializable {
        private Integer maChiTietKhamBenh;
        private Integer maDonThuoc;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChiTietDonThuoc.ChiTietDonThuocId)) return false;
            ChiTietDonThuoc.ChiTietDonThuocId that = (ChiTietDonThuoc.ChiTietDonThuocId) o;
            return Objects.equals(maChiTietKhamBenh, that.maChiTietKhamBenh) &&
                    Objects.equals(maDonThuoc, that.maDonThuoc);
        }

        @Override
        public int hashCode() {
            return Objects.hash(maChiTietKhamBenh, maDonThuoc);
        }
    }
}