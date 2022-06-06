package com.artzvrzn.mail.scheduler.view;

import com.artzvrzn.mail.scheduler.dao.api.IScheduledMailRepository;
import com.artzvrzn.mail.scheduler.dao.api.entity.ScheduledMailEntity;
import com.artzvrzn.mail.scheduler.exception.ValidationException;
import com.artzvrzn.mail.scheduler.model.Schedule;
import com.artzvrzn.mail.scheduler.model.ScheduledMail;
import com.artzvrzn.mail.scheduler.view.api.DateResolver;
import com.artzvrzn.mail.scheduler.view.api.IMailSchedulerService;
import com.artzvrzn.mail.scheduler.view.job.SendMailJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class MailReportSchedulerService implements IMailSchedulerService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private IScheduledMailRepository repository;
    @Autowired
    private ConversionService conversionService;

    @Override
    public void schedule(ScheduledMail scheduledMail) {
        UUID id = saveSchedule(scheduledMail);
        DateResolver.insertParamsDates(scheduledMail);
        JobDetail jobDetail = getJobDetail(id, scheduledMail);
        Trigger trigger = getCronTrigger(jobDetail, scheduledMail.getSchedule());
        scheduleJob(jobDetail, trigger);
    }

    @Override
    public Page<ScheduledMail> get(Pageable pageable) {
        return repository.findAll(pageable).map(e -> conversionService.convert(e, ScheduledMail.class));
    }

    @Override
    public ScheduledMail get(UUID id) {
        ScheduledMailEntity entity = getOrThrow(id);
        return conversionService.convert(entity, ScheduledMail.class);
    }

    @Override
    public void update(UUID id, long updated, ScheduledMail dto) {
        ScheduledMailEntity entity = getOrThrow(id);
        checkUpdated(entity, updated);
        DateResolver.insertParamsDates(dto);
        ScheduledMailEntity updatedEntity = updateEntity(entity, dto);
        try {
            repository.save(updatedEntity);
            scheduler.unscheduleJob(new TriggerKey(id.toString()));
            scheduler.deleteJob(new JobKey(id.toString()));
            JobDetail newJobDetail = getJobDetail(id, dto);
            Trigger newTrigger = getCronTrigger(newJobDetail, dto.getSchedule());
            scheduleJob(newJobDetail, newTrigger);
        } catch (SchedulerException exc) {
            throw new IllegalStateException("Failure during updating a schedule: ", exc);
        }
    }

    @Override
    public void delete(UUID id, long updated) {
        ScheduledMailEntity entity = repository.getById(id);
        checkUpdated(entity, updated);
        try {
            repository.delete(entity);
            scheduler.unscheduleJob(new TriggerKey(id.toString()));
            scheduler.deleteJob(new JobKey(id.toString()));
        } catch (SchedulerException exc) {
            throw new IllegalStateException("Failure during deleting a schedule: ", exc);
        }
    }

    private UUID saveSchedule(ScheduledMail scheduledMail) {
        UUID id = UUID.randomUUID();
        long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        scheduledMail.setId(id);
        scheduledMail.setCreated(currentTime);
        scheduledMail.setUpdated(currentTime);
        return repository.save(conversionService.convert(scheduledMail, ScheduledMailEntity.class)).getId();
    }

    private ScheduledMailEntity updateEntity(ScheduledMailEntity entity, ScheduledMail dto) {
        entity.setUpdated(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        entity.setReceiver(dto.getMailParams().getReceiverEmail());
        entity.setType(dto.getMailParams().getType());
        entity.setInitialParams(dto.getMailParams().getParams());
        entity.setSchedule(dto.getSchedule());
        return entity;
    }

    private void scheduleJob(JobDetail jobDetail, Trigger trigger) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException exc) {
            throw new IllegalStateException("Failure during scheduling a job: ", exc);
        }
    }

    private JobDetail getJobDetail(UUID id, ScheduledMail scheduledMail) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("params", scheduledMail.getMailParams());
        dataMap.put("schedule", scheduledMail.getSchedule());
        dataMap.put("id", id);
        return JobBuilder.newJob(SendMailJob.class)
                .storeDurably()
                .withIdentity(id.toString(), "reports")
                .usingJobData(dataMap)
                .build();
    }

    private Trigger getCronTrigger(JobDetail jobDetail, Schedule schedule) {
        jobDetail.getJobDataMap().put("schedule", schedule);
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "reports")
                .withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCronExpression())
                        .withMisfireHandlingInstructionFireAndProceed()
                        .inTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC)))
                .build();
    }

    private ScheduledMailEntity getOrThrow(UUID id) {
        Optional<ScheduledMailEntity> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new ValidationException("Schedule with id not found");
        }
        return optionalEntity.get();
    }

    private void checkUpdated(ScheduledMailEntity entity, long updated) {
        if (entity.getUpdated() != updated) {
            throw new ValidationException("Schedule has been already updated");
        }
    }
}
