package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.ChiTietChiSoDTO;
import com.nhom27.nhatkykhambenh.model.ChiTietChiSo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChiTietChiSoMapper {
    ChiTietChiSo toChiTietChiSo(ChiTietChiSoDTO chiTietChiSoDTO);

    ChiTietChiSoDTO toChiTietChiSoDTO(ChiTietChiSo chiTietChiSo);

    List<ChiTietChiSoDTO> toChiTietChiSoDtoList(List<ChiTietChiSo> chiTietChiSoList);
}