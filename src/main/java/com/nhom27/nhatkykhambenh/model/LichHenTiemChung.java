package com.nhom27.nhatkykhambenh.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LichHenTiemChung")
public class LichHenTiemChung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLichHenTiemChung")
    private Integer maLichHenTiemChung;

    @Column(name = "NoiTiemChung")
    private String noiTiemChung;

    @Column(name = "NgayHenTiem")
    private LocalDateTime ngayHenTiem;

    @OneToMany(mappedBy = "lichHenTiemChung", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<NguoiDungTiemChung> nguoiDungTiemChungList = new HashSet<>();
}