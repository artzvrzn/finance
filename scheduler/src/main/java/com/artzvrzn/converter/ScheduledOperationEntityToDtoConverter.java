package com.artzvrzn.converter;

import com.artzvrzn.dao.api.entity.ScheduledOperationEntity;
import com.artzvrzn.model.Operation;
import com.artzvrzn.model.Schedule;
import com.artzvrzn.model.ScheduledOperation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledOperationEntityToDtoConverter implements Converter<ScheduledOperationEntity, ScheduledOperation> {

    @Override
    public ScheduledOperation convert(ScheduledOperationEntity entity) {
        ScheduledOperation dto = new ScheduledOperation();
        dto.setUuid(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        Operation operation = new Operation();
        operation.setAccount(entity.getAccount());
        operation.setDescription(entity.getDescription());
        operation.setValue(entity.getValue());
        operation.setCurrency(entity.getCurrency());
        operation.setCategory(entity.getCategory());
        dto.setOperation(operation);
        Schedule schedule = new Schedule();
        schedule.setStartTime(entity.getStartTime());
        schedule.setStopTime(entity.getStopTime());
        schedule.setInterval(entity.getInterval());
        schedule.setTimeUnit(entity.getTimeUnit());
        dto.setSchedule(schedule);
        return dto;
    }
}
