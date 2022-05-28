package com.artzvrzn.dao.api.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "file_properties", schema = "app")
public class FilePropertyEntity {

    @Id
    @Column(name = "report_id", updatable = false)
    private UUID id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "report_id")
    private ReportEntity report;
    private String path;
    private String extension;

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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
