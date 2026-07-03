package com.project.ems.service.impl;

import com.project.ems.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendWelcomeEmail(String to, String employeeName,String department) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Welcome to Employee Management System");

        String body = """
                Dear %s,

                Welcome to our organization!

                Your employee account has been successfully created in the Employee Management System.

                Employee Details
                ---------------------------
                Name       : %s
                Department : %s

                We are excited to have you as part of our team.

                If you have any questions, please contact the HR department.

                Best Regards,
                HR Team
                Employee Management System
                """.formatted(employeeName, employeeName, department);
        message.setText(body);
        try {
            mailSender.send(message);
            System.out.println("✓ Welcome email sent successfully to: " + to);
        }
        catch (MailException ex) {
            System.err.println("✗ Failed to send email to " + to);
            System.err.println(ex.getMessage());
        }
    }
}