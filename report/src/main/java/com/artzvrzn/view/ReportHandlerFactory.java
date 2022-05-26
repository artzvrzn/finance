package com.artzvrzn.view;

import com.artzvrzn.model.ReportType;
import com.artzvrzn.view.api.IReportHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ReportHandlerFactory {

    @Autowired
    private ApplicationContext context;

    public IReportHandler getGenerator(ReportType type) {
        switch (type) {
            case BALANCE:
                return context.getBean(BalanceReportHandler.class);
            case BY_DATE:
                return context.getBean(ByDateReportHandler.class);
            case BY_CATEGORY:
                return context.getBean(ByCategoryReportHandler.class);
            default:
                throw new IllegalStateException(String.format("Cannot get generator of type %s", type));
        }
    }
}
