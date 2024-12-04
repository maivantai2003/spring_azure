package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.TongQuanDTO;
import com.nhom27.nhatkykhambenh.model.TongQuan;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TongQuanMapper {
    TongQuan toTongQuan(TongQuanDTO tongQuanDTO);

    TongQuanDTO toTongQuanDTO(TongQuan tongQuan);

    List<TongQuanDTO> toTongQuanDtoList(List<TongQuan> tongQuanList);
}