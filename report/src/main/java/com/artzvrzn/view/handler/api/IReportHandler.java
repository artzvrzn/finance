package com.artzvrzn.view.handler.api;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IReportHandler {

    ByteArrayOutputStream generate(Map<String, Object> params);

    String getFileFormat();
}
