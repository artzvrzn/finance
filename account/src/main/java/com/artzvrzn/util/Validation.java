package com.artzvrzn.util;

import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.Account;
import com.artzvrzn.model.AccountType;
import com.artzvrzn.model.Operation;
import com.artzvrzn.model.validation.ValidationError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Validation {

    private Validation(){}

    public static void validate(Account account) {
        if (account == null) {
            throw new ValidationException("Body is empty");
        }
        List<ValidationError> errors = new ArrayList<>();
        nullCheck("title", account.getTitle(), errors);
        nullCheck("description", account.getDescription(), errors);
        nullCheck("type", account.getType(), errors);
        nullCheck("currency", account.getCurrency(), errors);
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation error", errors);
        }
    }

    public static void validate(Operation operation) {
        if (operation == null) {
            throw new ValidationException("Body is empty");
        }
        List<ValidationError> errors = new ArrayList<>();
        nullCheck("description", operation.getDescription(), errors);
        nullCheck("category", operation.getCategory(), errors);
        nullCheck("currency", operation.getCurrency(), errors);
        nullCheck("date", operation.getDate(), errors);
        nullCheck("value", operation.getValue(), errors);
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation error", errors);
        }
    }

    private static void nullCheck(String field, Object object, List<ValidationError> errorList) {
        if (object == null) {
            errorList.add(new ValidationError(field, String.format("Field '%s' was not passed", field)));
        }
    }
}
