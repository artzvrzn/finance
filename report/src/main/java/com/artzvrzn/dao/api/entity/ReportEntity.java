package com.artzvrzn.dao.api.entity;

import com.artzvrzn.model.ReportType;
import com.artzvrzn.model.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
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
    @OneToOne(mappedBy = "report", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FilePropertyEntity filename;
    private Map<String, Object> params;

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

    public FilePropertyEntity getFilename() {
        return filename;
    }

    public void setFilename(FilePropertyEntity filename) {
        this.filename = filename;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
