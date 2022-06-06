package com.artzvrzn.mail.scheduler.converter;

import com.artzvrzn.mail.scheduler.dao.api.entity.ScheduledMailEntity;
import com.artzvrzn.mail.scheduler.model.ScheduledMail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMailDtoToEntityConverter implements Converter<ScheduledMail, ScheduledMailEntity> {

    @Override
    public ScheduledMailEntity convert(ScheduledMail dto) {
        ScheduledMailEntity entity = new ScheduledMailEntity();
        entity.setId(dto.getId());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        entity.setSchedule(dto.getSchedule());
        entity.setReceiver(dto.getMailParams().getReceiverEmail());
        entity.setType(dto.getMailParams().getType());
        entity.setInitialParams(dto.getMailParams().getParams());
        return entity;
    }
}
