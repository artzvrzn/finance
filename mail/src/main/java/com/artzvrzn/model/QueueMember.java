package com.artzvrzn.model;

import java.util.UUID;

public class QueueMember {

    private UUID reportId;
    private MailParams params;

    public QueueMember(UUID reportId, MailParams params) {
        this.reportId = reportId;
        this.params = params;
    }

    public UUID getReportId() {
        return reportId;
    }

    public void setReportId(UUID reportId) {
        this.reportId = reportId;
    }

    public MailParams getParams() {
        return params;
    }

    public void setParams(MailParams params) {
        this.params = params;
    }
}
