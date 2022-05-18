package com.artzvrzn.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportFactory {

    @Autowired
    private ObjectMapper mapper;

    public Report getReport(ReportType type, String json) {
        Report report = new Report();
        report.setType(type);
        try {
            report.setParams(resolveParams(type, json));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to parse json value");
        }
        report.setDescription("Description"); //TODO
        return report;
    }

    private Params resolveParams(ReportType type, String json) throws JsonProcessingException {
        switch (type) {
            case BALANCE:
                return mapper.readValue(json, ReportParamBalance.class);
            case BY_DATE:
                return mapper.readValue(json, ReportParamByDate.class);
            case BY_CATEGORY:
                return mapper.readValue(json, ReportParamByCategory.class);
            default:
                throw new IllegalArgumentException("Wrong report type");
        }
    }
}
