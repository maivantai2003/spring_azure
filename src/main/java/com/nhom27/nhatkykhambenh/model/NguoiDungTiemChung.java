package com.nhom27.nhatkykhambenh.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NguoiDungTiemChung")
@Builder
public class NguoiDungTiemChung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNguoiDungTiemChung")
    private Integer maNguoiDungTiemChung;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaNguoiDung", referencedColumnName = "MaNguoiDung")
    private NguoiDung nguoiDung;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MaLichHenTiemChung", referencedColumnName = "MaLichHenTiemChung")
    @JsonBackReference
    private LichHenTiemChung lichHenTiemChung;

    @Column(name = "TenVaccin")
    private String tenVaccin;
}