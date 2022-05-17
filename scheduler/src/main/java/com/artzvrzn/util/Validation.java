package com.artzvrzn.util;

import com.artzvrzn.model.Operation;
import com.artzvrzn.model.Schedule;
import com.artzvrzn.model.ScheduledOperation;
import com.artzvrzn.model.ValidationError;
import com.artzvrzn.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public final class Validation {

    private Validation() {}

    public static void validate(ScheduledOperation scheduledOperation) {
        if (scheduledOperation == null) {
            throw new ValidationException("Body is missing");
        }
        if (scheduledOperation.getOperation() == null) {
            throw new ValidationException("Operation was not passed");
        }
        if (scheduledOperation.getSchedule() == null) {
            throw new ValidationException("Schedule was not passed");
        }
        List<ValidationError> errors = new ArrayList<>();
        errors.addAll(Validation.validate(scheduledOperation.getOperation()));
        errors.addAll(Validation.validate(scheduledOperation.getSchedule()));
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation error", errors);
        }
    }

    public static List<ValidationError> validate(Operation operation) {
        List<ValidationError> errors = new ArrayList<>();
        nullCheck("account", operation.getAccount(), errors);
        nullCheck("value", operation.getValue(), errors);
        nullCheck("category", operation.getCategory(), errors);
        nullCheck("currency", operation.getCurrency(), errors);
        nullCheck("description", operation.getDescription(), errors);
        return errors;
    }

    public static List<ValidationError> validate(Schedule schedule) {
        List<ValidationError> errors = new ArrayList<>();
        nullCheck("time_unit", schedule.getTimeUnit(), errors);
        if (schedule.getInterval() < 0) {
            errors.add(new ValidationError("interval", "Interval cannot be negative"));
        }
        if (schedule.getStartTime() > schedule.getStopTime()) {
            errors.add(new ValidationError("start_time", "Start time cannot be after stop time"));
        }
        if (schedule.getStartTime() == schedule.getStopTime()) {
            errors.add(new ValidationError("stop_time", "Start time and stop time cannot be the same"));
        }

        return errors;
    }

    private static void nullCheck(String field, Object object, List<ValidationError> errorList) {
        if (object == null) {
            errorList.add(new ValidationError(field, String.format("Field '%s' was not passed", field)));
        }
    }
}
