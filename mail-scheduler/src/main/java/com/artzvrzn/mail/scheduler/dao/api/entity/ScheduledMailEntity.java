package com.artzvrzn.mail.scheduler.dao.api.entity;

import com.artzvrzn.mail.scheduler.model.Schedule;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "scheduled_mails", schema = "app")
public class ScheduledMailEntity {

    @Id
    private UUID id;
    private long created;
    private long updated;
    private Schedule schedule;
    private String receiver;
    private String type;
    private Map<String, Object> initialParams;

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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getInitialParams() {
        return initialParams;
    }

    public void setInitialParams(Map<String, Object> initialParams) {
        this.initialParams = initialParams;
    }
}
