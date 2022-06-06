package com.artzvrzn.mail.scheduler.converter;

import com.artzvrzn.mail.scheduler.dao.api.entity.ScheduledMailEntity;
import com.artzvrzn.mail.scheduler.model.MailParams;
import com.artzvrzn.mail.scheduler.model.ScheduledMail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMailEntityToDtoConverter implements Converter<ScheduledMailEntity, ScheduledMail> {

    @Override
    public ScheduledMail convert(ScheduledMailEntity entity) {
        ScheduledMail dto = new ScheduledMail();
        dto.setId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        dto.setSchedule(entity.getSchedule());
        MailParams params = new MailParams();
        params.setType(entity.getType());
        params.setReceiverEmail(entity.getReceiver());
        params.setParams(entity.getInitialParams());
        dto.setMailParams(params);
        return dto;
    }
}
