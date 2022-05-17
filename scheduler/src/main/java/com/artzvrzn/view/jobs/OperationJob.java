package com.artzvrzn.view.jobs;

import com.artzvrzn.view.RestService;
import com.artzvrzn.view.SchedulerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OperationJob implements Job {

    private final RestService restService;
    private final SchedulerService schedulerService;

    public OperationJob(RestService restService, SchedulerService schedulerService) {
        this.restService = restService;
        this.schedulerService = schedulerService;
    }

    @Override
    public void execute(JobExecutionContext jobContext) {
        UUID id = (UUID) jobContext.getMergedJobDataMap().get("id");
        restService.postScheduledOperation(schedulerService.get(id));
    }
}
