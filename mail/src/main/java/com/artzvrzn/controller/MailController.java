package com.artzvrzn.controller;

import com.artzvrzn.model.MailParams;
import com.artzvrzn.view.api.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail/")
public class MailController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private IMailService service;

    @GetMapping("/report/send/")
    @ResponseStatus(HttpStatus.OK)
    public void sendMail(@RequestBody MailParams params) throws InterruptedException {
        service.send(params);
    }
}
