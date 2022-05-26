package com.artzvrzn.controller;

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
    public ResponseEntity<ByteArrayResource> produceReport(@PathVariable("uuid") UUID id) {
        byte[] reportBytes = reportService.export(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + id + ".xlsx")
                .contentType(MediaType
                        .parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new ByteArrayResource(reportBytes));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.HEAD)
    public ResponseEntity<?> isAvailable(@PathVariable("uuid") UUID id) {
        if (reportService.isReady(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
