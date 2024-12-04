package com.nhom27.nhatkykhambenh.service.interfaces;

import com.nhom27.nhatkykhambenh.model.NguoiDung;

import java.time.LocalDateTime;

public interface IEmailService {
    String sendEmail(NguoiDung nguoiDung) throws Exception;

    boolean hasExpired(LocalDateTime expirDateTime);
    void sendSimpleEmail(String email, String subject, String content);
}
