package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietTiemChung")
@IdClass(ChiTietTiemChung.ChiTietTiemChungId.class)
public class ChiTietTiemChung {
    @Id
    @Column(name = "MaTiemChung")
    private Integer maTiemChung;

    @Id
    @Column(name = "MaNguoiDung")
    private Integer maNguoiDung;

    @Id
    @Column(name = "NgayTiem")
    private LocalDateTime ngayTiem;

    @Column(name = "NguoiTiem")
    private String nguoiTiem;

    @Column(name = "MuiTiemSo")
    private String muiTiemSo;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    public ChiTietTiemChungId getId() {
        return new ChiTietTiemChungId(maTiemChung, maNguoiDung, ngayTiem);
    }

    @ManyToOne
    @JoinColumn(name = "MaTiemChung", referencedColumnName = "MaTiemChung", insertable = false, updatable = false)
    private TiemChung tiemChung;

    @ManyToOne
    @JoinColumn(name = "MaNguoiDung", referencedColumnName = "MaNguoiDung", insertable = false, updatable = false)
    private NguoiDung nguoiDung;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChiTietTiemChungId implements Serializable {
        private Integer maTiemChung;
        private Integer maNguoiDung;
        private LocalDateTime ngayTiem;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChiTietTiemChung.ChiTietTiemChungId)) return false;
            ChiTietTiemChung.ChiTietTiemChungId that = (ChiTietTiemChung.ChiTietTiemChungId) o;
            return Objects.equals(maTiemChung, that.maTiemChung) &&
                    Objects.equals(maNguoiDung, that.maNguoiDung) &&
                    Objects.equals(ngayTiem, that.ngayTiem);
        }

        @Override
        public int hashCode() {
            return Objects.hash(maTiemChung, maNguoiDung, ngayTiem);
        }
    }
}
