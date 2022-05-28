package com.artzvrzn.view.handler;

import com.artzvrzn.model.ReportType;
import com.artzvrzn.view.handler.api.IReportHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReportHandlerFactory {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Communicator communicator;
    @Autowired
    private ParamsParser paramsParser;
    @Autowired
    private ConversionService conversionService;

    public IReportHandler getHandler(ReportType type) {
        switch (type) {
            case BALANCE:
                return new BalanceReportHandler(communicator, paramsParser);
            case BY_DATE:
                return new ByDateReportHandler(communicator, paramsParser, conversionService);
            case BY_CATEGORY:
//                return context.getBean(ByCategoryReportHandler.class);
                return new ByCategoryReportHandler(communicator, paramsParser, conversionService);
            default:
                throw new IllegalStateException(String.format("Cannot get handler of type %s", type));
        }
    }
}
