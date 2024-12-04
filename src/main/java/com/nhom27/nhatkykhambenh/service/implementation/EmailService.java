package com.nhom27.nhatkykhambenh.service.implementation;

import com.nhom27.nhatkykhambenh.model.NguoiDung;
import com.nhom27.nhatkykhambenh.model.PasswordResetToken;
import com.nhom27.nhatkykhambenh.repository.ITokenRepository;
import com.nhom27.nhatkykhambenh.service.interfaces.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ITokenRepository tokenRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.password}")
    private  String password;

    @Override
    public String sendEmail(NguoiDung nguoiDung) throws Exception {
        try {
            String resetLink = generateResetToken(nguoiDung);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(nguoiDung.getEmail());

            message.setSubject("WELLCOME TO WEBSITE");
            message.setText("Xin chào, \n\n"
                    + "Vui lòng nhấn vào liên kết sau để đặt lại mật khẩu của bạn. Lưu ý rằng liên kết này chỉ có hiệu lực trong 10 phút: " + resetLink + ". \n\n"
                    + "Trân trọng, \n"
                    + "ABC");

            javaMailSender.send(message);

            return "success";
        }
        catch (Exception e) {
            e.printStackTrace();
            return"error";
        }
    }

    @Async
    public void sendSimpleEmail(String email, String subject, String content){
        System.out.println(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }


    public String generateResetToken(NguoiDung nguoiDung) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusMinutes(10);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(uuid.toString());
        passwordResetToken.setExpiryDateTime(expireTime);
        passwordResetToken.setNguoiDung(nguoiDung);

        PasswordResetToken token = tokenRepository.save(passwordResetToken);
        if(token != null) {
            String endpointUrl = "http://localhost:8080/resetpassword";
            return endpointUrl + "/" + token.getToken();
        }

        return "";
    }

    @Override
    public boolean hasExpired(LocalDateTime expirDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expirDateTime.isAfter(currentDateTime);
    }
}
