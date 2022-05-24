package com.artzvrzn.view;

import com.artzvrzn.dao.api.IReportRepository;
import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.model.Report;
import com.artzvrzn.model.ReportType;
import com.artzvrzn.model.Status;
import com.artzvrzn.view.api.IReportExecutor;
import com.artzvrzn.view.api.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class ReportService implements IReportService {

    @Autowired
    private IReportExecutor executor;
    @Autowired
    private IReportRepository reportRepository;
    @Autowired
    private ConversionService conversionService;

    @Override
    public void create(ReportType type, Map<String, Object> params) {
        Report report = new Report();
        report.setId(UUID.randomUUID());
        LocalDateTime currentTime = LocalDateTime.now();
        report.setCreated(currentTime);
        report.setUpdated(currentTime);
        report.setStatus(Status.LOADED);
        report.setParams(params);
        report.setType(type);
        ReportEntity entity = conversionService.convert(report, ReportEntity.class);
        reportRepository.save(entity);
        executor.execute(entity.getId());
    }

    @Override
    public Page<Report> get(Pageable pageable) {
        return reportRepository.findAll(pageable).map(e -> conversionService.convert(e, Report.class));
    }

    @Override
    public boolean isReady(UUID id) {
        return executor.isReady(id);
    }

    @Override
    public byte[] export(UUID id) {
        return executor.getFile(id);
    }
}
