package com.artzvrzn.mail.scheduler.view.api;

import com.artzvrzn.mail.scheduler.model.Schedule;
import com.artzvrzn.mail.scheduler.model.ScheduledMail;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;

public final class DateResolver {

    private DateResolver() {}

    public static void insertParamsDates(ScheduledMail scheduledMail) {
        LocalDate dateFrom = LocalDate.now(ZoneOffset.UTC);
        LocalDate dateTo;
        switch (scheduledMail.getSchedule()) {
            case DAILY:
                dateTo = dateFrom.plusDays(1);
                break;
            case WEEKLY:
                dateFrom = dateFrom.with(DayOfWeek.MONDAY);
                dateTo = dateFrom.plusWeeks(1);
                break;
            case MONTHLY:
                dateFrom = dateFrom.withDayOfMonth(1);
                dateTo = dateFrom.plusMonths(1);
                break;
            case YEARLY:
                dateFrom = dateFrom.withDayOfYear(1);
                dateTo = dateFrom.plusYears(1);
                break;
            default:
                dateFrom = dateFrom.with(DayOfWeek.MONDAY);
                dateTo = dateFrom.plusWeeks(1);
                break;
        }
        scheduledMail.getMailParams().getParams().put("from", dateFrom.toString());
        scheduledMail.getMailParams().getParams().put("to", dateTo.toString());
    }

    public static void updateParamsDates(Schedule schedule, Map<String, Object> reportParams) {
//        JobDetail jobDetail = scheduler.getJobDetail(new JobKey(scheduledMail.getId().toString()));
        LocalDate dateFrom = LocalDate.parse(reportParams.get("to").toString());
        LocalDate dateTo;
        switch (schedule) {
            case DAILY:
                dateTo = dateFrom.plusDays(1);
                break;
            case WEEKLY:
                dateTo = dateFrom.plusWeeks(1);
                break;
            case MONTHLY:
                dateTo = dateFrom.plusMonths(1);
                break;
            case YEARLY:
                dateTo = dateFrom.plusYears(1);
                break;
            default:
                dateTo = dateFrom.plusWeeks(1);
        }
        reportParams.put("from", dateFrom.toString());
        reportParams.put("to", dateTo.toString());
    }
}
