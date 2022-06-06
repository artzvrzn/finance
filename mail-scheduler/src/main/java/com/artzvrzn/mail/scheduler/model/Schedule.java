package com.artzvrzn.mail.scheduler.model;

import java.io.Serializable;

public enum Schedule implements Serializable {

    TEST("0/30 0/1 * 1/1 * ? *"),
    DAILY("0 8 * * *"),
    WEEKLY("0 8 * * MON"),
    MONTHLY("0 8 1 * *"),
    YEARLY("0 8 1 1 *");

    private final String cronExpression;

    Schedule(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCronExpression() {
        return cronExpression;
    }


}
