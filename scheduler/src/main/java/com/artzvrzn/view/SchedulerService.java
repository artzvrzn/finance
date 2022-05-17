package com.artzvrzn.view;

import com.artzvrzn.dao.api.IScheduledOperationRepository;
import com.artzvrzn.dao.api.entity.ScheduledOperationEntity;
import com.artzvrzn.view.jobs.OperationJob;
import com.artzvrzn.model.Schedule;
import com.artzvrzn.model.ScheduledOperation;
import com.artzvrzn.util.TimeUtils;
import com.artzvrzn.util.Validation;
import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.view.api.ISchedulerService;
import org.quartz.*;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.artzvrzn.util.TimeUtils.dateOfEpochSeconds;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SchedulerService implements ISchedulerService {

    private final String GROUP = "operations";
    private final Scheduler scheduler;
    private final IScheduledOperationRepository repository;
    private final ConversionService conversionService;

    public SchedulerService(
            Scheduler scheduler, IScheduledOperationRepository repository, ConversionService conversionService) {
        this.scheduler = scheduler;
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @Override
    public void create(ScheduledOperation scheduledOperation) {
        Validation.validate(scheduledOperation);
        scheduledOperation.setUuid(UUID.randomUUID());
        long currentTime = TimeUtils.getCurrentSeconds();
        scheduledOperation.setCreated(currentTime);
        scheduledOperation.setUpdated(currentTime);
        JobDetail jobDetail = buildJobDetail(scheduledOperation.getUuid());
        Trigger trigger = buildTrigger(scheduledOperation);
        repository.save(conversionService.convert(scheduledOperation, ScheduledOperationEntity.class));
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new IllegalArgumentException("Failed to create job " + scheduledOperation.getUuid(), e.getCause());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScheduledOperation> get(Pageable pageable) {
        Page<ScheduledOperationEntity> operations = repository.findAll(pageable);
        return operations.map(e -> conversionService.convert(e, ScheduledOperation.class));
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduledOperation get(UUID id) {
        return conversionService.convert(getByIdFromRepository(id), ScheduledOperation.class);
    }

    @Override
    public void update(UUID id, long updated, ScheduledOperation scheduledOperation) {
        Validation.validate(scheduledOperation);
        ScheduledOperationEntity entity = updateFields(getByIdFromRepository(id), scheduledOperation);
        if (entity.getUpdated() != updated) {
            throw new ValidationException(String.format("Operation %s has been already updated", id));
        }
        entity.setUpdated(TimeUtils.getCurrentSeconds());
        repository.save(entity);
        try {
            scheduler.deleteJob(new JobKey(id.toString(), GROUP));
            scheduler.scheduleJob(buildJobDetail(id), buildTrigger(scheduledOperation));
        } catch (SchedulerException e) {
            throw new IllegalArgumentException("Failed to update job " + id, e.getCause());
        }
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
        try {
            scheduler.unscheduleJob(new TriggerKey(id.toString(), GROUP));
            scheduler.deleteJob(new JobKey(id.toString(), GROUP));
        } catch (SchedulerException e) {
            throw new IllegalArgumentException("Failed to delete job", e.getCause());
        }
    }

    private ScheduledOperationEntity getByIdFromRepository(UUID id) {
        Optional<ScheduledOperationEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ValidationException("Cannot find scheduled operation with id " + id);
        }
        return optional.get();
    }

    private ScheduledOperationEntity updateFields(ScheduledOperationEntity entity, ScheduledOperation dto) {
        entity.setAccount(dto.getOperation().getAccount());
        entity.setDescription(dto.getOperation().getDescription());
        entity.setValue(dto.getOperation().getValue());
        entity.setCurrency(dto.getOperation().getCurrency());
        entity.setCategory(dto.getOperation().getCategory());
        entity.setStartTime(dto.getSchedule().getStartTime());
        entity.setStopTime(dto.getSchedule().getStopTime());
        entity.setInterval(dto.getSchedule().getInterval());
        if (dto.getSchedule().getTimeUnit() != null) {
            entity.setTimeUnit(dto.getSchedule().getTimeUnit());
        }
        return entity;
    }

    private JobDetail buildJobDetail(UUID scheduledOperationId) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("id", scheduledOperationId);
        return newJob(OperationJob.class)
                .withIdentity(scheduledOperationId.toString(), GROUP)
                .usingJobData(dataMap)
                .build();
    }

    private Trigger buildTrigger(ScheduledOperation scheduledOperation) {
        Schedule schedule = scheduledOperation.getSchedule();
        TriggerBuilder<Trigger> builder = newTrigger()
                .withIdentity(scheduledOperation.getUuid().toString(), GROUP);
        if (schedule.getInterval() != 0) {
            builder.withSchedule(simpleSchedule()
                    .withIntervalInMilliseconds(schedule.getInterval() * schedule.getTimeUnit().getSeconds() * 1000)
                    .repeatForever());
        }

        if (schedule.getStartTime() == 0 || schedule.getStartTime() < System.currentTimeMillis() / 1000) {
            builder.startNow();
        } else {
            builder.startAt(dateOfEpochSeconds(schedule.getStartTime()));
        }

        if (schedule.getStopTime() != 0) {
            builder.endAt(dateOfEpochSeconds(schedule.getStopTime()));
        }
        return builder.build();
    }
}
