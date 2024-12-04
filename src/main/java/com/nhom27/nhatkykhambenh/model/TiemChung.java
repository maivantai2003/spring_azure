package com.nhom27.nhatkykhambenh.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TiemChung")
public class TiemChung {
    public static final String OBJ_NAME = "TiemChung";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTiemChung")
    private Integer maTiemChung;

    @Column(name = "TenVacXin")
    private String tenVacXin;

    @Column(name = "NoiTiemChung")
    private String noiTiemChung;

    @Column(name = "SoMuiTiem")
    private Integer soMuiTiem;

    @Column(name = "TrangThai")
    private Boolean trangThai;

}