package com.artzvrzn.view;

import com.artzvrzn.dao.api.FilenameRepository;
import com.artzvrzn.dao.api.IReportRepository;
import com.artzvrzn.dao.api.entity.FilePropertyEntity;
import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.ReportFile;
import com.artzvrzn.model.Status;
import com.artzvrzn.view.api.IReportExecutor;
import com.artzvrzn.view.handler.ParamsParser;
import com.artzvrzn.view.handler.ReportHandlerFactory;
import com.artzvrzn.view.handler.api.IReportHandler;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
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
    @Autowired
    private ParamsParser paramsParser;
    @Value("${reports-storage-path}")
    private String storagePath;

    @Override
    public void execute(UUID id) {
        ReportEntity entity = reportRepository.getById(id);
        IReportHandler handler = reportHandlerFactory.getHandler(entity.getType());
        paramsParser.validate(entity.getType(), entity.getParams());
        executorService.execute(() -> {
            reportRepository.updateStatus(id, Status.PROGRESS, LocalDateTime.now());
            try {
//                Thread.sleep(10000l);
                byte[] bytes = handler.generate(entity.getParams());
                saveFile(id, bytes, handler.getFileFormat());
            } catch (Exception exc) {
                reportRepository.updateStatus(id, Status.ERROR, LocalDateTime.now());
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public ReportFile readFile(UUID id) {
        FilePropertyEntity fileProperty = filenameRepository.getByReportId(id);
        if (fileProperty == null) {
            throw new ValidationException(String.format("Report with id %s doesn't exist", id));
        }
        Path path = Path.of(fileProperty.getPath());
        String extension = FileNameUtils.getExtension(fileProperty.getExtension());
        try {
            ReportFile reportFile = new ReportFile();
            reportFile.setContent(Files.readAllBytes(path));
            reportFile.setExtension(extension);
            reportFile.setMediaType(getMediaType(extension));
            return reportFile;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read file: ", e);
        }
    }

    private void saveFile(UUID id, byte[] bytes, String extension) {
        Path path = Path.of(storagePath, id.toString() + extension);
        try {
            Files.write(path, bytes);
            reportRepository.updateStatus(id, Status.DONE, LocalDateTime.now());
            filenameRepository.updateFilename(id, path.toString(), extension);
        } catch (IOException e) {
            reportRepository.updateStatus(id, Status.ERROR, LocalDateTime.now());
            throw new IllegalStateException("Failure during saving report: ", e);
        }
    }

    private MediaType getMediaType(String fileExtension) {
        switch (fileExtension) {
            case "xlsx":
                return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            default:
                throw new IllegalStateException(
                        String.format("MediaType for %s extension is not defined", fileExtension));
        }
    }
}
