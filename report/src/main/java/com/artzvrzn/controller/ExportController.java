package com.artzvrzn.controller;

import com.artzvrzn.model.ReportFile;
import com.artzvrzn.view.api.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("account/{uuid}/export")
public class ExportController {

    @Autowired
    private IReportService reportService;

    @GetMapping(value = {"", "/"}, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<ByteArrayResource> exportReport(@PathVariable("uuid") UUID id) {
        ReportFile reportFile = reportService.export(id);
        if (reportFile == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + id + "." + reportFile.getExtension())
                .contentType(reportFile.getMediaType())
                .body(new ByteArrayResource(reportFile.getContent()));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.HEAD)
    public ResponseEntity<?> isAvailable(@PathVariable("uuid") UUID id) {
        if (reportService.isReady(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
