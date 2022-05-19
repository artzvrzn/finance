package com.artzvrzn.controller;

import com.artzvrzn.model.report.Report;
import com.artzvrzn.model.report.ReportFactory;
import com.artzvrzn.model.report.ReportType;
import com.artzvrzn.view.api.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportFactory reportFactory;
    @Autowired
    private IReportService reportService;

    @PostMapping(value = {"/{type}", "/{type}/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReport(@PathVariable("type") ReportType type, @RequestBody String reportParam) {
        Report report = reportFactory.getReport(type, reportParam);
        reportService.create(report);
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Report> getReports(@Param("page") int page, @Param("size") int size) {
        PageRequest request = PageRequest.of(page, size);
        return reportService.get(request);
    }
}
