package com.artzvrzn.view.handler;

import com.artzvrzn.model.ReportType;
import com.artzvrzn.view.handler.BalanceReportHandler;
import com.artzvrzn.view.handler.ByCategoryReportHandler;
import com.artzvrzn.view.handler.ByDateReportHandler;
import com.artzvrzn.view.handler.Communicator;
import com.artzvrzn.view.handler.api.IReportHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ReportHandlerFactory {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Communicator communicator;
    @Autowired
    private ObjectMapper mapper;

    public IReportHandler getGenerator(ReportType type) {
        switch (type) {
            case BALANCE:
                return new BalanceReportHandler(communicator, mapper);
            case BY_DATE:
                return new ByDateReportHandler(communicator, mapper);
            case BY_CATEGORY:
//                return context.getBean(ByCategoryReportHandler.class);
                return new ByCategoryReportHandler(communicator, mapper);
            default:
                throw new IllegalStateException(String.format("Cannot get generator of type %s", type));
        }
    }
}
