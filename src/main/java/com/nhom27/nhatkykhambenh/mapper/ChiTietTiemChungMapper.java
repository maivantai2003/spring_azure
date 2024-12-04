package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.ChiTietTiemChungDTO;
import com.nhom27.nhatkykhambenh.model.ChiTietTiemChung;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChiTietTiemChungMapper {
    ChiTietTiemChung toChiTietTiemChung(ChiTietTiemChungDTO chiTietTiemChungDTO);

    ChiTietTiemChungDTO toChiTietTiemChungDTO(ChiTietTiemChung chiTietTiemChung);

    List<ChiTietTiemChungDTO> toChiTietTiemChungDtoList(List<ChiTietTiemChung> chiTietTiemChungList);

}