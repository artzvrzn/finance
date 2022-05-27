package com.artzvrzn.view.handler.api;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IReportHandler {

    byte[] generate(Map<String, Object> params);

    void validate(Map<String, Object> params);

    String convertDescription(Map<String, Object> params);

}
