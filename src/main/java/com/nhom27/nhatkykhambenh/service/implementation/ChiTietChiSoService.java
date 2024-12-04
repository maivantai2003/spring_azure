package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.dto.ChiTietChiSoDTO;
import com.nhom27.nhatkykhambenh.exception.SaveDataException;
import com.nhom27.nhatkykhambenh.mapper.ChiTietChiSoMapper;
import com.nhom27.nhatkykhambenh.model.*;
import com.nhom27.nhatkykhambenh.repository.IChiSoRepo;
import com.nhom27.nhatkykhambenh.repository.IChiTietBenhRepo;
import com.nhom27.nhatkykhambenh.repository.IChiTietChiSoRepo;
import com.nhom27.nhatkykhambenh.repository.ITongQuanRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.IChiTietChiSoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChiTietChiSoService implements IChiTietChiSoService {

    @Autowired
    private IChiTietChiSoRepo chiTietChiSoRepo;

    @Autowired
    private ChiTietChiSoMapper chiTietChiSoMapper;

    @Autowired
    private ITongQuanRepo tongQuanRepo;

    @Autowired
    private IChiTietBenhRepo chiTietBenhRepo;

    @Autowired
    private IChiSoRepo chiSoRepo;

    @Override
    public List<ChiTietChiSoDTO> getDsChiTietChiSo(Integer maChiSo, Integer maTongQuan) {
        List<ChiTietChiSo> chiTietChiSoList = chiTietChiSoRepo.findByMaTongQuanAndMaChiSo(maTongQuan, maChiSo);
        return chiTietChiSoMapper.toChiTietChiSoDtoList(chiTietChiSoList);
    }

    @Override
    public void saveCTChiSo(ChiTietChiSoDTO chiTietChiSoDTO) {
        ChiTietChiSo chiTietChiSo = chiTietChiSoMapper.toChiTietChiSo(chiTietChiSoDTO);
        try {
            chiTietChiSoRepo.save(chiTietChiSo);

            Optional<ChiTietChiSo> maxTimeChiSo = chiTietChiSoRepo.
                    findTopByMaTongQuanAndMaChiSoOrderByThoiGianDoDesc(
                        chiTietChiSo.getMaTongQuan(), chiTietChiSo.getMaChiSo()
                    );

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String maxTime = sdf.format(maxTimeChiSo.get().getThoiGianDo());
            String currentTime = sdf.format(chiTietChiSo.getThoiGianDo());

            if (maxTime.equals(currentTime)) {
                TongQuan tongQuan = tongQuanRepo.findById(chiTietChiSo.getMaTongQuan()).orElse(null);
                if (tongQuan != null) {
                    ChiSo chiSo = chiSoRepo.findById(chiTietChiSoDTO.getMaChiSo()).orElse(null);

                    tongQuan = setLoaiChiSo(tongQuan, chiSo, chiTietChiSo);

                    tongQuanRepo.save(tongQuan);
                }
            }
        }
        catch (Exception e) {
            throw new SaveDataException(TiemChung.OBJ_NAME);
        }
    }

    public TongQuan setLoaiChiSo(TongQuan tongQuan, ChiSo chiSo, ChiTietChiSo chiTietChiSo) {
        if (chiSo != null && "duongHuyet".equalsIgnoreCase(chiSo.getLoaiChiSo())) {
            tongQuan.setDuongHuyet(chiTietChiSo.getKetQuaDo());
        }
        else if (chiSo != null && "canNang".equalsIgnoreCase(chiSo.getLoaiChiSo())) {
            tongQuan.setCanNang(chiTietChiSo.getKetQuaDo());
        }
        else if (chiSo != null && "chiSoBMI".equalsIgnoreCase(chiSo.getLoaiChiSo())) {
            tongQuan.setChiSoBMI(chiTietChiSo.getKetQuaDo());
        }
        else if (chiSo != null && "chieuCao".equalsIgnoreCase(chiSo.getLoaiChiSo())) {
            tongQuan.setChieuCao(chiTietChiSo.getKetQuaDo());
        }
        else if (chiSo != null && "huyetAp".equalsIgnoreCase(chiSo.getLoaiChiSo())) {
            tongQuan.setHuyetAp(chiTietChiSo.getKetQuaDo());
        }
        else if (chiSo != null && "nhietDo".equalsIgnoreCase(chiSo.getLoaiChiSo())) {
            tongQuan.setNhietDo(chiTietChiSo.getKetQuaDo());
        }
        else if (chiSo != null && "nhipTim".equalsIgnoreCase(chiSo.getLoaiChiSo())) {
            tongQuan.setNhipTim(chiTietChiSo.getKetQuaDo());
        }

        return tongQuan;
    }

    public void checkChiTietBenh() {

    }

    @Transactional
    @Override
    public void deleteChiTietChiSo(Integer maTongQuan, Integer maChiSo, Date thoiGianDo) {
        chiTietChiSoRepo.deleteByMaTongQuanAndMaChiSoAndThoiGianDo(maTongQuan, maChiSo, thoiGianDo);
    }


}
