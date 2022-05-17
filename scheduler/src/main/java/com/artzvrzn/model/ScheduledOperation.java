package com.artzvrzn.model;

import com.artzvrzn.view.jobs.OperationJob;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.quartz.*;

import java.util.UUID;

import static com.artzvrzn.util.TimeUtils.dateOfEpochSeconds;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScheduledOperation {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long created;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long updated;
    private Schedule schedule;
    private Operation operation;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public JobDetail buildJobDetail() {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("id", uuid.toString());
        return newJob(OperationJob.class)
                .withIdentity(uuid.toString(), "operations")
                .usingJobData(dataMap)
                .build();
    }

    public Trigger buildTrigger() {
        TriggerBuilder<SimpleTrigger> builder = newTrigger()
                .withIdentity(uuid.toString(), "operations")
                .withSchedule(simpleSchedule()
                        .withIntervalInMilliseconds(schedule.getInterval() * schedule.getTimeUnit().getSeconds() * 1000)
                        .repeatForever());

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
