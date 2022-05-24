package com.artzvrzn.dao.api.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "reports_path", schema = "app")
public class FilenameEntity {

    @Id
    @Column(name = "report_id", updatable = false)
    private UUID id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "report_id")
    private ReportEntity report;
    private String path;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ReportEntity getReport() {
        return report;
    }

    public void setReport(ReportEntity report) {
        this.report = report;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
