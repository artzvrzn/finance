package com.artzvrzn.mail.scheduler.view.job;

import com.artzvrzn.mail.scheduler.view.MailCommunicator;
import com.artzvrzn.mail.scheduler.model.MailParams;
import com.artzvrzn.mail.scheduler.model.Schedule;
import com.artzvrzn.mail.scheduler.view.api.DateResolver;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class SendMailJob extends QuartzJobBean {

    @Autowired
    private MailCommunicator communicator;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap dataMap = context.getMergedJobDataMap();
            MailParams mailParams = (MailParams) context.getJobDetail().getJobDataMap().get("params");
            Schedule schedule = (Schedule) dataMap.get("schedule");
            communicator.sendMailRequest(mailParams);
            DateResolver.updateParamsDates(schedule, mailParams.getParams());
            context.getScheduler().addJob(context.getJobDetail(), true);
        } catch (Exception e) {
            try {
                context.getScheduler().unscheduleJob(context.getTrigger().getKey());
                throw new JobExecutionException(e.getMessage(), true);
            } catch (SchedulerException e2) {
                throw new JobExecutionException(e.getMessage(), true);
            }
        }
    }
}
