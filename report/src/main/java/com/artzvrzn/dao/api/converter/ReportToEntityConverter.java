package com.artzvrzn.dao.api.converter;

import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.model.Report;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportToEntityConverter implements Converter<Report, ReportEntity> {

    @Override
    public ReportEntity convert(Report dto) {
        ReportEntity entity = new ReportEntity();
        entity.setId(dto.getId());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        entity.setParams(dto.getParams());
        return entity;
    }
}
