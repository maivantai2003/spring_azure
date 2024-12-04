package com.nhom27.nhatkykhambenh.mapper;

import com.nhom27.nhatkykhambenh.dto.LichHenKhamDTO;
import com.nhom27.nhatkykhambenh.dto.LichHenTiemChungDTO;
import com.nhom27.nhatkykhambenh.model.LichHenKham;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LichHenKhamMapper {
    LichHenKhamDTO toDTO(LichHenKham lichHenKham);
    List<LichHenKhamDTO> toListDTO(List<LichHenKham> lichHenKham);
    LichHenKham toEntity(LichHenKhamDTO lichHenKhamDTO);
}
