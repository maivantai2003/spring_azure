package com.nhom27.nhatkykhambenh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TiemChungDTO {

    private Integer maTiemChung;

    private String tenVacXin;

    private String noiTiemChung;

    private Integer soMuiTiem;

    private Boolean trangThai;
}
