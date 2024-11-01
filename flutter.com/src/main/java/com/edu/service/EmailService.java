package com.edu.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendPasswordResetCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Mã đặt lại mật khẩu");
        message.setText("Mã của bạn là: " + code);
        emailSender.send(message);
    }
    
    @Async
    public void sendSuccessOrder(String to) {
        String subject = "Đơn hàng được giao thành công";
        String text = "<h1>Chúc mừng!</h1>"
        		+ "<br>Đơn hàng của bạn đã được giao tới bạn thành công."
        		+ "<br>Cảm ơn bạn đã mua sắm!";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true để gửi dưới dạng HTML nếu cần
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
