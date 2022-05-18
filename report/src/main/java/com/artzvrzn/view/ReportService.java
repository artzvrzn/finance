package com.artzvrzn.view;

import com.artzvrzn.dao.api.ReportRepository;
import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.model.Report;
import com.artzvrzn.model.Status;
import com.artzvrzn.view.api.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReportService implements IReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ConversionService conversionService;

    @Override
    public void create(Report report) {
        if (report == null) {
            throw new IllegalArgumentException("Report is null");
        }
        report.setId(UUID.randomUUID());
        LocalDateTime current = LocalDateTime.now();
        report.setCreated(current);
        report.setUpdated(current);
        report.setStatus(Status.LOADED);
        ReportEntity entity = conversionService.convert(report, ReportEntity.class);
        reportRepository.save(entity);
    }

    @Override
    public Page<Report> get(Pageable pageable) {
        return reportRepository.findAll(pageable).map(e -> conversionService.convert(e, Report.class));
    }
}
