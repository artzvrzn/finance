package com.artzvrzn.view.api;

import com.artzvrzn.model.report.Report;
import org.springframework.http.MediaType;

public interface IReportProducer {

    byte[] produce(Report report);

    MediaType getMediaType();

    String getContentDispositionValue();

}
