package com.artzvrzn.view.api;

import com.artzvrzn.model.Report;
import com.artzvrzn.model.ReportFile;
import com.artzvrzn.model.ReportType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public interface IReportService {

    UUID create(ReportType type, Map<String, Object> params);

    Page<Report> get(Pageable pageable);

    boolean isReady(UUID id);

    ReportFile export(UUID id);

}
