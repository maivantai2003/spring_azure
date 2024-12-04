package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.model.LichHenTiemChung;
import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.NguoiDungTiemChung;
import com.nhom27.nhatkykhambenh.repository.ILichHenTiemChungRepo;
import com.nhom27.nhatkykhambenh.repository.INguoiDungTiemChung;
import com.nhom27.nhatkykhambenh.service.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;



@Service
public class NhacNhoTiemChungService {

    @Autowired
    IEmailService emailService;

    @Autowired
    ILichHenTiemChungRepo lichHenTiemChungRepo;

    @Autowired
    INguoiDungTiemChung nguoiDungTiemChungRepo;

    @Scheduled(cron = "0 06 9 * * ?")
    public void sendVaccinationRemider() {
        LocalDateTime startOfTomorrow = LocalDateTime.now().plusDays(1).with(LocalTime.MIN);
        LocalDateTime endOfTomorrow = LocalDateTime.now().plusDays(1).with(LocalTime.MAX);
        List<LichHenTiemChung> lichHenTiemChungList = lichHenTiemChungRepo.findByNgayHenTiemBetween(startOfTomorrow, endOfTomorrow);
        lichHenTiemChungList.forEach(lichHenTiemChung -> {
            List<NguoiDungTiemChung> nguoiDungTiemChungList = nguoiDungTiemChungRepo.findAllByLichHenTiemChung(lichHenTiemChung);
            String emailContent = getMessage(lichHenTiemChung.getNgayHenTiem(), lichHenTiemChung.getNoiTiemChung());
            for (NguoiDungTiemChung nguoiDungTiemChung : nguoiDungTiemChungList) {
                NguoiDung nguoiDung = nguoiDungTiemChung.getNguoiDung();
                try {
                    if (nguoiDung.getEmail() != null) {
                        System.out.println(nguoiDung.getEmail());
                        emailService.sendSimpleEmail(nguoiDung.getEmail(), "Nhắc Nhở Tiêm Chủng", emailContent);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private String getMessage(LocalDateTime ngayHenTiemChung, String noiTiemChung) {
        String template = "Bạn có lịch hẹn tiêm chủng vào ngày mai lúc %s:%s tại %s";
        return String.format(template, ngayHenTiemChung.getHour(), ngayHenTiemChung.getMinute(), noiTiemChung);
    }
}
