package com.artzvrzn.view.api;

import com.artzvrzn.model.ReportFile;

import java.util.UUID;

public interface IReportExecutor {

    void execute(UUID id);

    ReportFile readFile(UUID id);
}
