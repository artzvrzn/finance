package com.artzvrzn.view;

import com.artzvrzn.model.MailParams;
import com.artzvrzn.model.QueueMember;
import com.artzvrzn.view.api.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReportMailService implements IMailService {

    @Autowired
    private ReportCommunicator communicator;
    @Autowired
    private ScheduledMailer sender;

    @Override
    public void send(MailParams params) {
        UUID id = communicator.postRequestReport(params);
        sender.addToSendingQueue(new QueueMember(id, params));
    }
}
