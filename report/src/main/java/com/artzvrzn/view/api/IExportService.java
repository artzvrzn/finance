package com.artzvrzn.view.api;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IExportService {

    ResponseEntity<byte[]> export(UUID id);

    boolean isReportAvailable(UUID id);
}
