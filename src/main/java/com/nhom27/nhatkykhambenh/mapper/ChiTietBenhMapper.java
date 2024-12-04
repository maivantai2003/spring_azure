package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.ChiTietBenhDTO;
import com.nhom27.nhatkykhambenh.model.ChiTietBenh;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChiTietBenhMapper {
    ChiTietBenh toChiTietBenh(ChiTietBenhDTO chiTietBenhDTO);

    ChiTietBenhDTO toChiTietBenhDTO(ChiTietBenh chiTietBenh);

    List<ChiTietBenhDTO> toChiTietBenhDtoList(List<ChiTietBenh> chiTietBenhList);
}
