package com.artzvrzn.mail.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.artzvrzn.dao"})
public class MailSchedulerApplication {

    public static void main(String... args) {
        SpringApplication.run(MailSchedulerApplication.class, args);
    }
}
