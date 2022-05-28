package com.artzvrzn.converter;

import com.artzvrzn.dao.api.entity.FilePropertyEntity;
import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.model.Report;
import com.artzvrzn.model.ReportType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class ReportToEntityConverter implements Converter<Report, ReportEntity> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public ReportEntity convert(Report dto) {
        ReportEntity entity = new ReportEntity();
        entity.setId(dto.getId());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        FilePropertyEntity fileProperty = new FilePropertyEntity();
        fileProperty.setPath(dto.getFilename());
        fileProperty.setReport(entity);
        entity.setFilename(fileProperty);
        entity.setDescription(dto.getDescription() == null ?
                generateDescription(dto.getType(), dto.getParams()) : dto.getDescription());
        entity.setParams(dto.getParams());
        return entity;
    }

    private String generateDescription(ReportType type, Map<String, Object> params) {
        switch (type) {
            case BY_DATE:
                LocalDate from =
                        params.get("from") == null ? null : mapper.convertValue(params.get("from"), LocalDate.class);
                LocalDate to =
                        params.get("from") == null ? null : mapper.convertValue(params.get("to"), LocalDate.class);
                return String.format("Совершенные операции с %s по %s", from, to);
            case BY_CATEGORY:
                return "Совершенные операции по определенным категориям";
            default:
                return "Выписка по счетам";
        }
    }

}
