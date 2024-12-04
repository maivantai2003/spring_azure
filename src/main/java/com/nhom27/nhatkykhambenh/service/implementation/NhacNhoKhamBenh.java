package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.model.LichHenKham;
import com.nhom27.nhatkykhambenh.repository.ILichHenKhamBenhRepo;
import com.nhom27.nhatkykhambenh.service.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class NhacNhoKhamBenh {
    @Autowired
    private ILichHenKhamBenhRepo lichHenKhamBenhRepo;
    @Autowired
    private IEmailService emailService;
    @Scheduled(cron = "0 05 18 * * ?")
    public void sendSeeDoctorsRemind(){
        LocalDateTime startOfTomorrow = LocalDateTime.now().plusDays(1).with(LocalTime.MIN);
        LocalDateTime endOfTomorrow = LocalDateTime.now().plusDays(1).with(LocalTime.MAX);
        List<LichHenKham> lichHenKhamList = this.lichHenKhamBenhRepo.findByThoiGianHenBetween(startOfTomorrow, endOfTomorrow);
        for (LichHenKham lichHenKham: lichHenKhamList) {
            String message = getMessage(lichHenKham.getThoiGianHen().toLocalDateTime(), lichHenKham.getKhamBenh().getBenhVien());
            emailService.sendSimpleEmail(lichHenKham.getKhamBenh().getNguoiDung().getEmail(), "Nhắn nhở khám bệnh", message);
        }
    }
    private String getMessage(LocalDateTime ngayHenTiemChung, String noiTiemChung) {
        String template = "Bạn có lịch hẹn khám bệnh vào ngày mai lúc %s:%s tại %s";
        return String.format(template, ngayHenTiemChung.getHour(), ngayHenTiemChung.getMinute(), noiTiemChung);
    }
}
