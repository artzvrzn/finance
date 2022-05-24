package com.artzvrzn.view;

import com.artzvrzn.dao.api.FilenameRepository;
import com.artzvrzn.dao.api.IReportRepository;
import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.model.Status;
import com.artzvrzn.view.api.IReportExecutor;
import com.artzvrzn.view.api.IReportHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportExecutor implements IReportExecutor {

    private final ExecutorService executorService;
    private final HashMap<UUID, Future<?>> futures;
    private final ReportHandlerFactory reportHandlerFactory;
    @Autowired
    private IReportRepository reportRepository;
    @Autowired
    private FilenameRepository filenameRepository;
    @Value("${executor-thread-pool}")
    private int threadCount;
    @Value("${reports-storage-path}")
    private String storagePath;

    public ReportExecutor(ReportHandlerFactory factory) {
        this.reportHandlerFactory = factory;
        this.executorService = Executors.newFixedThreadPool(5);
        this.futures = new HashMap<>();
    }

    @Override
    public void execute(UUID id) {
        ReportEntity entity = reportRepository.getById(id);
        IReportHandler handler = reportHandlerFactory.getGenerator(entity.getType());
        reportRepository.updateStatus(id, Status.PROGRESS, LocalDateTime.now());
        executorService.execute(() -> writeAsFile(id, handler.handle(entity.getParams())));
//        futures.put(id, f);
    }

    @Override
    public boolean isReady(UUID id) {
        return reportRepository.getById(id).getStatus().equals(Status.DONE);
    }

    @Override
    public byte[] getFile(UUID id) {
        Path filename = Path.of(filenameRepository.getById(id).getPath());
        try {
            return Files.readAllBytes(filename);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read file", e);
        }
    }

    private void writeAsFile(UUID id, byte[] bytes) {
        Path path = Path.of(storagePath, id.toString() + ".xlsx");
        try {
            Files.write(path, bytes);
            ReportEntity entity = reportRepository.getById(id);
            reportRepository.updateStatus(id, Status.DONE, LocalDateTime.now());
            filenameRepository.updateFilename(id, path.toString());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
