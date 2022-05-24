package com.artzvrzn.view.api;

import com.artzvrzn.model.Report;
import com.artzvrzn.model.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface IReportService {

    void create(ReportType type, Map<String, Object> params);

    Page<Report> get(Pageable pageable);

    boolean isReady(UUID id);

    byte[] export(UUID id);

}
