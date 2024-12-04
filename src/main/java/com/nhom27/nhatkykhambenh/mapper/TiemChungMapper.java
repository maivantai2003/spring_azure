package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.TiemChungDTO;
import com.nhom27.nhatkykhambenh.model.TiemChung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TiemChungMapper {
    TiemChung toTiemChung(TiemChungDTO tiemChungDTO);
    TiemChungDTO toTiemChungDTO(TiemChung tiemChung);
    List<TiemChungDTO> toTiemChungDtoList(List<TiemChung> tiemChungList);

    @Mapping(target = "maTiemChung", ignore = true)
    void updateTiemChungFromDTO(TiemChungDTO tiemChungDTO, @MappingTarget TiemChung tiemChung);
}
