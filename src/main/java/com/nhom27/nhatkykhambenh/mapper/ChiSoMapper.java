package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.ChiSoDTO;
import com.nhom27.nhatkykhambenh.model.ChiSo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChiSoMapper {
    ChiSo toChiSo(ChiSoDTO chiSoDTO);

    ChiSoDTO toChiSoDTO(ChiSo chiSo);

    List<ChiSoDTO> toChiSoDtoList(List<ChiSo> chiSoList);
}
