package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.ChiTietDonThuocDTO;
import com.nhom27.nhatkykhambenh.dto.XetNghiemDTO;
import com.nhom27.nhatkykhambenh.model.ChiTietDonThuoc;
import com.nhom27.nhatkykhambenh.model.XetNghiem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface ChiTietDonThuocMapper {
    ChiTietDonThuoc toChiTietDonThuoc(ChiTietDonThuocDTO chiTietDonThuocDTO);

    ChiTietDonThuocDTO toChiTietDonThuocDTO(ChiTietDonThuoc chiTietDonThuoc);

    List<ChiTietDonThuocDTO> toChiTietDonThuocDtoList(List<ChiTietDonThuoc> chiTietDonThuocList);

}
