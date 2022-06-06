package com.artzvrzn.mail.scheduler.view.api;

import com.artzvrzn.mail.scheduler.model.ScheduledMail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IMailSchedulerService {

    void schedule(ScheduledMail scheduledMail);

    Page<ScheduledMail> get(Pageable pageable);

    ScheduledMail get(UUID id);

    void update(UUID id, long updated, ScheduledMail scheduledMail);

    void delete(UUID id, long updated);
}
