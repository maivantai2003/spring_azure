package com.nhom27.nhatkykhambenh.mapper;


import com.nhom27.nhatkykhambenh.dto.LichHenTiemChungDTO;
import com.nhom27.nhatkykhambenh.dto.NguoiDungTiemChungDTO;
import com.nhom27.nhatkykhambenh.model.LichHenTiemChung;
import com.nhom27.nhatkykhambenh.model.NguoiDungTiemChung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LichHenTiemChungMapper {
//    public NguoiDungTiemChung toNguoiDungTiemChung(NguoiDungTiemChungDTO nguoiDungTiemChungDTO);
//    public NguoiDungTiemChungDTO toNguoiDungTiemChungDTO(NguoiDungTiemChung nguoiDungTiemChung);
//    public Set<NguoiDungTiemChung> toNguoiDungTiemChungSet(Set<NguoiDungTiemChungDTO> nguoiDungTiemChungDTOSet);
    public LichHenTiemChung toLichHenTiemChung(LichHenTiemChungDTO lichHenTiemChungDTO);
    public Set<NguoiDungTiemChungDTO> toNguoiDungTiemChungDTOSet(Set<NguoiDungTiemChung> nguoiDungTiemChungSet);
//    @Mapping(source = "maLichHenTiemChung", target = "maLichHenTiemChung")
//    @Mapping(source = "noiTiemChung", target = "noiTiemChung")
//    @Mapping(source = "ngayHenTiem", target = "ngayHenTiem")
//    @Mapping(source = "nguoiDungTiemChungList", target = "nguoiDungTiemChungList")
    public LichHenTiemChungDTO toLichHenTiemChungDTO(LichHenTiemChung lichHenTiemChung);

    public List<LichHenTiemChungDTO> toListLichHenTiemChungDTO(List<LichHenTiemChung> lichHenTiemChung);

}
