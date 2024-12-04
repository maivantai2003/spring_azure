package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.GiaDinhDTO;
import com.nhom27.nhatkykhambenh.model.GiaDinh;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GiaDinhMapper {
    GiaDinh toGiaDinh(GiaDinhDTO giaDinhDTO);

    GiaDinhDTO toGiaDinhDTO(GiaDinh giaDinh);

    List<GiaDinhDTO> toGiaDinhDtoList(List<GiaDinh> giaDinhList);
}