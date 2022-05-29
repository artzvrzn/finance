package com.artzvrzn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
public class MailSenderConfig {

    @Bean
    public ExecutorService getExecutor() {
        return Executors.newFixedThreadPool(5);
    }
}
