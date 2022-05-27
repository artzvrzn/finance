package com.artzvrzn.view;

import com.artzvrzn.dao.api.FilenameRepository;
import com.artzvrzn.dao.api.IReportRepository;
import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.model.Status;
import com.artzvrzn.view.api.IReportExecutor;
import com.artzvrzn.view.handler.ReportHandlerFactory;
import com.artzvrzn.view.handler.api.IReportHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportExecutor implements IReportExecutor {

    @Autowired
    private ReportHandlerFactory reportHandlerFactory;
    @Autowired
    private IReportRepository reportRepository;
    @Autowired
    private FilenameRepository filenameRepository;
    @Autowired
    private ExecutorService executorService;
    @Value("${reports-storage-path}")
    private String storagePath;

    @Override
    public void execute(UUID id) {
        ReportEntity entity = reportRepository.getById(id);
        IReportHandler handler = reportHandlerFactory.getGenerator(entity.getType());
        handler.validate(entity.getParams());
        executorService.execute(() -> {
            reportRepository.updateStatus(id, Status.PROGRESS, LocalDateTime.now());
            try {
                byte[] bytes = handler.generate(entity.getParams());
                saveFile(id, bytes);
            } catch (Exception exc) {
                reportRepository.updateStatus(id, Status.ERROR, LocalDateTime.now());
            }
        });
    }

    private void saveFile(UUID id, byte[] bytes) {
        Path path = Path.of(storagePath, id.toString() + ".xlsx");
        try {
            Files.write(path, bytes);
            reportRepository.updateStatus(id, Status.DONE, LocalDateTime.now());
            filenameRepository.updateFilename(id, path.toString());
        } catch (IOException e) {
            reportRepository.updateStatus(id, Status.ERROR, LocalDateTime.now());
            throw new IllegalStateException("Failure during saving report file", e);
        }
    }
}
