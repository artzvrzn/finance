package com.artzvrzn.view.api;

import com.artzvrzn.model.report.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IReportService {

    void create(Report report);

    Page<Report> get(Pageable pageable);

    Report get(UUID id);

}
