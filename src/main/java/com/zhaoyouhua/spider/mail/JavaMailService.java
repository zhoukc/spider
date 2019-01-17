package com.zhaoyouhua.spider.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JavaMailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendMail(String sender, String receiver, String title, String text) {
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        log.info("------------开始发送邮件--------------");
        mainMessage.setFrom(sender);
        mainMessage.setTo(receiver);
        mainMessage.setSubject(title);
        mainMessage.setText(text);
        javaMailSender.send(mainMessage);
        log.info("------------发送成功--------------");
    }

    public void sendGroupMail(String sender, String title, String text,String... receiver) {
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        log.info("------------开始发送邮件--------------");
        mainMessage.setFrom(sender);
        mainMessage.setTo(receiver);
        mainMessage.setSubject(title);
        mainMessage.setText(text);
        javaMailSender.send(mainMessage);
        log.info("------------发送成功--------------");
    }

}
