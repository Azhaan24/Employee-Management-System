package com.project.ems.service;

public interface EmailService {
    void sendWelcomeEmail(String to,String employeeName,String department);
}