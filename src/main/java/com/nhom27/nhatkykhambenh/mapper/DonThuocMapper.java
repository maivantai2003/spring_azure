package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.DonThuocDTO;
import com.nhom27.nhatkykhambenh.model.DonThuoc;
import com.nhom27.nhatkykhambenh.model.DonThuoc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DonThuocMapper {
     DonThuoc toDonThuoc(DonThuocDTO donThuocDTO);

     DonThuocDTO toDonThuocDTO(DonThuoc donThuoc);

     List<DonThuocDTO> toDonThuocDtoList(List<DonThuoc> donThuocList);
}
