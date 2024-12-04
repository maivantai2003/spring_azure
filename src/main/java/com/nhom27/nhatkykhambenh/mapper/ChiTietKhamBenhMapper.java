package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.ChiTietKhamBenhDTO;
import com.nhom27.nhatkykhambenh.model.ChiTietKhamBenh;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChiTietKhamBenhMapper {
    ChiTietKhamBenh toChiTietKhamBenh(ChiTietKhamBenhDTO chiTietKhamBenhDTO);

    ChiTietKhamBenhDTO toChiTietKhamBenhDTO(ChiTietKhamBenh chiTietKhamBenh);

    List<ChiTietKhamBenhDTO> toChiTietKhamBenhDtoList(List<ChiTietKhamBenh> chiTietKhamBenhList);

    @Mapping(target = "maChiTietKhamBenh", ignore = true)
    void updateChiTietKhamBenhFromDTO(ChiTietKhamBenhDTO chiTietKhamBenhDTO, @MappingTarget ChiTietKhamBenh chiTietKhamBenh);
}
