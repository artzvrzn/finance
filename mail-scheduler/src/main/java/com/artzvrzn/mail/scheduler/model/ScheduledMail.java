package com.artzvrzn.mail.scheduler.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScheduledMail {

    private UUID id;
    @JsonProperty("dt_created")
    private long created;
    @JsonProperty("dt_updated")
    private long updated;

    private Schedule schedule;
    private MailParams mailParams;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public MailParams getMailParams() {
        return mailParams;
    }

    public void setMailParams(MailParams mailParams) {
        this.mailParams = mailParams;
    }
}
