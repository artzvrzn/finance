package com.artzvrzn.converter;

import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.model.Report;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportEntityToDtoConverter implements Converter<ReportEntity, Report> {

    @Override
    public Report convert(ReportEntity entity) {
        Report dto = new Report();
        dto.setId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        dto.setType(entity.getType());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setFilename(entity.getFilename().getPath());
        dto.setParams(entity.getParams());
        return dto;
    }
}
