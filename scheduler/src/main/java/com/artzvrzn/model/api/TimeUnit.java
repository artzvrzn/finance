package com.artzvrzn.model.api;

import java.io.Serializable;

public enum TimeUnit implements Serializable {

    SECOND(1),
    MINUTE(60),
    HOUR(3600),
    DAY(86400),
    WEEK(604800),
    MONTH(2628000),
    YEAR(31536000);

    private final int seconds;

    TimeUnit(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }


}
