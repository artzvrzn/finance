package com.artzvrzn.dao.api.entity;

import com.artzvrzn.model.report.ReportParam;
import com.artzvrzn.model.report.ReportType;
import com.artzvrzn.model.report.Status;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reports", schema = "app")
public class ReportEntity {

    @Id
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Status status;
    private ReportType type;
    private String description;

    private ReportParam params;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportParam getParams() {
        return params;
    }

    public void setParams(ReportParam params) {
        this.params = params;
    }
}
