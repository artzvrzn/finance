package com.artzvrzn.view;

import com.artzvrzn.model.report.Report;
import com.artzvrzn.view.api.IExportService;
import com.artzvrzn.view.api.IReportProducer;
import com.artzvrzn.view.api.IReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReportExportService implements IExportService {

    private final IReportService reportService;
    private final IReportProducer reportProducer;

    public ReportExportService(IReportService reportService, IReportProducer reportProducer) {
        this.reportService = reportService;
        this.reportProducer = reportProducer;
    }

    @Override
    public ResponseEntity<byte[]> export(UUID id) {
        Report report = reportService.get(id);
        byte[] reportBytes = reportProducer.produce(report);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, reportProducer.getContentDispositionValue())
                .contentType(reportProducer.getMediaType())
                .body(reportBytes);
    }

    @Override
    public boolean isReportAvailable(UUID id) {
        try {
            reportService.get(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
