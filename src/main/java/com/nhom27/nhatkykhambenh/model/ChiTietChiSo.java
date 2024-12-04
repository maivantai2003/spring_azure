package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietChiSo")
@IdClass(ChiTietChiSo.ChiTietChiSoId.class)
public class ChiTietChiSo {
    @Id
    @Column(name = "MaChiSo")
    private Integer maChiSo;

    @Id
    @Column(name = "MaTongQuan")
    private Integer maTongQuan;

    @Id
    @Column(name = "ThoiGianDo")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianDo;

    @Column(name = "KetQuaDo", length = 250)
    private String ketQuaDo;

    @ManyToOne
    @JoinColumn(name = "MaChiSo", referencedColumnName = "MaChiSo", insertable = false, updatable = false)
    private ChiSo chiSo;

    @ManyToOne
    @JoinColumn(name = "MaTongQuan", referencedColumnName = "MaTongQuan", insertable = false, updatable = false)
    private TongQuan tongQuan;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChiTietChiSoId implements Serializable {
        private Integer maChiSo;
        private Integer maTongQuan;
        private Date thoiGianDo;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChiTietChiSoId)) return false;
            ChiTietChiSoId that = (ChiTietChiSoId) o;
            return Objects.equals(maChiSo, that.maChiSo) &&
                    Objects.equals(maTongQuan, that.maTongQuan) &&
                    Objects.equals(thoiGianDo, that.thoiGianDo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(maChiSo, maTongQuan, thoiGianDo);
        }
    }
}
