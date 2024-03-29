package com.twm.community.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Component
public class MailClient {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private  String from;

    public void sendMail(String to, String subject, String content){
       try {
           MimeMessage message = mailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(message);
           helper.setFrom(from);
           helper.setTo(to);
           helper.setSubject(subject);
           helper.setText(content, true);
           mailSender.send(helper.getMimeMessage());
       } catch (MessagingException e){
           log.error("邮件发送错误"+e.getMessage());
       }

    }






}
