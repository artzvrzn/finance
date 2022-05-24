package com.artzvrzn.view.api;

import java.util.UUID;

public interface IReportExecutor {

    void execute(UUID id);

    boolean isReady(UUID id);

    byte[] getFile(UUID id);

}
