package com.artzvrzn.view;

import com.artzvrzn.model.MailParams;
import com.artzvrzn.model.QueueMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class ScheduledMailer {

    @Autowired
    private ReportCommunicator communicator;
    @Autowired
    private JavaMailSender mailSender;
    private final Queue<QueueMember> awaiting = new LinkedBlockingDeque<>();

    public void addToSendingQueue(QueueMember qm) {
        awaiting.add(qm);
    }

    @Scheduled(fixedRate = 1000L)
    private void submitTasks() {
        int size = awaiting.size();
        for (int i = 0; i <= size; i++) {
            QueueMember qm = awaiting.poll();
            if (qm == null || qm.getParams() == null) {
                continue;
            }
            if (!communicator.isReportAvailable(qm.getReportId())) {
                awaiting.add(qm);
            } else {
                CompletableFuture.runAsync(() -> send(qm.getReportId(), qm.getParams()));
            }
        }
    }

    private void send(UUID id, MailParams mailParams) {
        File file = communicator.getRequestReport(id);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(mailParams.getReceiverEmail());
            helper.setSubject("Report");
            helper.setText("your report");
            helper.addAttachment(file.getName(), file);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Error during sending a mail", e);
        }
    }
}
