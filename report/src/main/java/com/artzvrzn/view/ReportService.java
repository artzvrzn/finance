package com.artzvrzn.view;

import com.artzvrzn.dao.api.IFilePropsRepository;
import com.artzvrzn.dao.api.IReportRepository;
import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.model.Report;
import com.artzvrzn.model.ReportFile;
import com.artzvrzn.model.ReportType;
import com.artzvrzn.model.Status;
import com.artzvrzn.view.api.IReportExecutor;
import com.artzvrzn.view.api.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportService implements IReportService {

    @Autowired
    private IReportExecutor executor;
    @Autowired
    private IReportRepository reportRepository;
    @Autowired
    private IFilePropsRepository filenameRepository;
    @Autowired
    private ConversionService conversionService;

    @Override
    public UUID create(ReportType type, Map<String, Object> params) {
        Report report = createReport(type, params);
        ReportEntity entity = conversionService.convert(report, ReportEntity.class);
        reportRepository.save(entity);
        executor.execute(entity.getId());
        return report.getId();
    }

    @Override
    public Page<Report> get(Pageable pageable) {
        return reportRepository.findAll(pageable).map(e -> conversionService.convert(e, Report.class));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isReady(UUID id) {
        ReportEntity entity = reportRepository.findByIdIfStatus(id, Status.DONE);
        return entity != null;
    }

    @Override
    public ReportFile export(UUID id) {
        return executor.readFile(id);
    }

    private Report createReport(ReportType type, Map<String, Object> params) {
        Report report = new Report();
        report.setId(UUID.randomUUID());
        LocalDateTime currentTime = LocalDateTime.now();
        report.setCreated(currentTime);
        report.setUpdated(currentTime);
        report.setStatus(Status.LOADED);
        report.setParams(params);
        report.setType(type);
        return report;
    }
}
