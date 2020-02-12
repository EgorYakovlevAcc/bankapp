package com.presentation.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value(value = "${spring.mail.host}")
    private String hostName;

    @Value(value = "${spring.mail.port}")
    private Integer hostPort;

    @Value(value = "${spring.mail.protocol}")
    private String protocol;

    @Value(value = "${spring.mail.username}")
    private String username;

    @Value(value = "${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(hostName);
        javaMailSender.setPort(hostPort);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties mailProperties = javaMailSender.getJavaMailProperties();
        mailProperties.put("mail.transport.protocol", protocol);
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.debug", "true");

        javaMailSender.setJavaMailProperties(mailProperties);

        return javaMailSender;
    }
}
