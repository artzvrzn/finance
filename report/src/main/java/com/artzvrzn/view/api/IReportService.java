package com.artzvrzn.view.api;

import com.artzvrzn.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReportService {

    void create(Report report);

    Page<Report> get(Pageable pageable);

}
