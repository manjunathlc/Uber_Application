package com.jsp.uber.services.impl;

import com.jsp.uber.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
//        javaMailSender.send(to -> {
//            to.setTo(toEmail);
//            to.setSubject(subject);
//            to.setText(body);
//        });


        try {
            SimpleMailMessage simpleMailMessage= new SimpleMailMessage();

            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);

            log.info("Email has been successfully sent");
        } catch (Exception e) {
            throw new RuntimeException("Error sending email to "+ toEmail + " with subject "
                    + subject + " and body " + body + " - " + e.getMessage());
        }

    }

    @Override
    public void sendEmail(String[] toEmail, String subject, String body) {
        try {
            SimpleMailMessage simpleMailMessage= new SimpleMailMessage();

            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);

            log.info("Email has been successfully sent");
        } catch (Exception e) {
            throw new RuntimeException("Error sending the  email " + e.getMessage());
        }

    }
}
