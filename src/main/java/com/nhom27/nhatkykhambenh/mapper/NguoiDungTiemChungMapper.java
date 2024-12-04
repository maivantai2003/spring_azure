package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.NguoiDungTiemChungDTO;
import com.nhom27.nhatkykhambenh.model.NguoiDungTiemChung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NguoiDungTiemChungMapper {
    public NguoiDungTiemChung toNguoiDungTiemChung(NguoiDungTiemChungDTO nguoiDungTiemChungDTO);
    public NguoiDungTiemChungDTO toNguoiDungTiemChungDTO(NguoiDungTiemChung nguoiDungTiemChung);
    public Set<NguoiDungTiemChung> toNguoiDungTiemChungSet(Set<NguoiDungTiemChungDTO> nguoiDungTiemChungDTOSet);
    public Set<NguoiDungTiemChungDTO> toNguoiDungTiemChungDTOSet(Set<NguoiDungTiemChung> nguoiDungTiemChungSet);
}

