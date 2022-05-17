package com.artzvrzn.converter;

import com.artzvrzn.dao.api.entity.ScheduledOperationEntity;
import com.artzvrzn.model.ScheduledOperation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledOperationDtoToEntityConverter implements Converter<ScheduledOperation, ScheduledOperationEntity> {

    @Override
    public ScheduledOperationEntity convert(ScheduledOperation dto) {
        ScheduledOperationEntity entity = new ScheduledOperationEntity();
        entity.setId(dto.getUuid());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        entity.setAccount(dto.getOperation().getAccount());
        entity.setDescription(dto.getOperation().getDescription());
        entity.setValue(dto.getOperation().getValue());
        entity.setCurrency(dto.getOperation().getCurrency());
        entity.setCategory(dto.getOperation().getCategory());
        entity.setStartTime(dto.getSchedule().getStartTime());
        entity.setStopTime(dto.getSchedule().getStopTime());
        entity.setInterval(dto.getSchedule().getInterval());
        entity.setTimeUnit(dto.getSchedule().getTimeUnit());
        return entity;
    }
}
