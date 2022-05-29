package com.artzvrzn.view.api;

import com.artzvrzn.model.MailParams;

public interface IScheduledMailer {

    void addToSendingQueue(MailParams params);
}
