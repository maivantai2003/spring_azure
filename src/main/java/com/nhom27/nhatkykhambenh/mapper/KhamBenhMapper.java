package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.KhamBenhDTO;
import com.nhom27.nhatkykhambenh.dto.TiemChungDTO;
import com.nhom27.nhatkykhambenh.model.KhamBenh;
import com.nhom27.nhatkykhambenh.model.TiemChung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface KhamBenhMapper {
    public KhamBenh toKhamBenh(KhamBenhDTO khamBenhDTO);

    public KhamBenhDTO toKhamBenhDTO(KhamBenh khamBenh);

    public List<KhamBenhDTO> toKhamBenhDtoList(List<KhamBenh> khamBenhList);

    @Mapping(target = "maKhamBenh", ignore = true)
    void updateKhamBenhFromDTO(KhamBenhDTO khamBenhDTO, @MappingTarget KhamBenh khamBenh);
}
