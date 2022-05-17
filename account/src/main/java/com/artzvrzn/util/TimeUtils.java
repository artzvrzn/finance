package com.artzvrzn.util;

import java.time.Instant;
import java.util.Date;

public final class TimeUtils {

    private TimeUtils(){}

    public static Date dateOfEpochSeconds(long seconds) {
        return Date.from(Instant.ofEpochSecond(seconds));
    }

    public static long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}
