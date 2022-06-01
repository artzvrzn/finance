package com.artzvrzn.controller;

import com.artzvrzn.model.ReportType;
import com.artzvrzn.view.api.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/backend/report")
public class BackendController {

    @Autowired
    private IReportService reportService;

    @PostMapping(value = {"/{type}", "/{type}/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createReport(@PathVariable("type") ReportType type, @RequestBody Map<String, Object> reportParams) {
        return reportService.create(type, reportParams);
    }

    @GetMapping(value = "/valid_type")
    public ResponseEntity<?> isValid(@Param("type") String type) {
        if (type != null && ReportType.fromString(type) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
