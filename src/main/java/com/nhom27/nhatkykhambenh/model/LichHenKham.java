package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LichHenKham")
public class LichHenKham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHenKham")
    private Integer maHenKham;

    @Column(name = "ThoiGianHen")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp thoiGianHen;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @ManyToOne()
    @JoinColumn(name = "MaKhamBenh",referencedColumnName = "MaKhamBenh")
    private KhamBenh khamBenh;
}
