package com.example.leon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(mailPort);
        javaMailSender.setPassword(mailPassword);
        javaMailSender.setUsername(mailUsername);

        /*
        * Этот блок кода получает объект `Properties`, который содержит дополнительные свойства для настройки SMTP-соединения.
        * В данном случае, свойство `mail.smtp.starttls.enable` устанавливается в `true`, что включает поддержку STARTTLS — расширение протокола SMTP,
        * которое позволяет использовать TLS для шифрования соединения.
        * */
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.starttls.enable", "true");
        return javaMailSender;
    }

}
