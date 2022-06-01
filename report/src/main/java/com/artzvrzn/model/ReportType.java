package com.artzvrzn.model;

public enum ReportType {

    BALANCE, BY_DATE, BY_CATEGORY;

    public static ReportType fromString(String type) {
        for (ReportType reportType: ReportType.values()) {
            if (reportType.name().equals(type)) {
                return reportType;
            }
        }
        return null;
    }

}
