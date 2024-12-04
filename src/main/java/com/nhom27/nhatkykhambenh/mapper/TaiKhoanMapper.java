package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.TaiKhoanDTO;
import com.nhom27.nhatkykhambenh.model.TaiKhoan;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaiKhoanMapper {
    TaiKhoan toTaiKhoan(TaiKhoanDTO taiKhoanDTO);

    TaiKhoanDTO toTaiKhoanDTO(TaiKhoan taiKhoan);

    List<TaiKhoanDTO> toTaiKhoanDtoList(List<TaiKhoan> taiKhoanList);
}