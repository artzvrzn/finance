package com.artzvrzn.view.api;

import org.springframework.http.MediaType;

import java.util.UUID;

public interface ExcelReportProducer extends IReportProducer {

    @Override
    default MediaType getMediaType() {
        return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    default String getContentDispositionValue() {
        return "attachment; filename=" + UUID.randomUUID() + ".xlsx";
    }
}
