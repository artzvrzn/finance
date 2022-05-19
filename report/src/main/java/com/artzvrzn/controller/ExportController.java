package com.artzvrzn.controller;

import com.artzvrzn.model.report.Report;
import com.artzvrzn.view.api.IExportService;
import com.artzvrzn.view.api.IReportProducer;
import com.artzvrzn.view.api.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("account/{uuid}/report")
public class ExportController {

    private final IExportService exportService;

    public ExportController(IExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> produceReport(@PathVariable("uuid") UUID id) {
        return exportService.export(id);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.HEAD)
    public ResponseEntity<?> reportAvailability(@PathVariable("uuid") UUID id) {
        if (exportService.isReportAvailable(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
