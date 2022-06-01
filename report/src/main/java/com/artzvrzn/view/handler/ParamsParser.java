package com.artzvrzn.view.handler;

import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.Account;
import com.artzvrzn.model.Category;
import com.artzvrzn.model.ReportType;
import com.artzvrzn.model.errors.ValidationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class ParamsParser {

    private static final String ACCOUNT_KEY = "accounts";
    private static final String FROM_KEY = "from";
    private static final String TO_KEY = "to";
    private static final String CATEGORY_KEY = "categories";

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Communicator communicator;

    public LocalDate getFrom(Map<String, Object> params) {
        return getDate(params, FROM_KEY);
    }

    public LocalDate getTo(Map<String, Object> params) {
        return getDate(params, TO_KEY);
    }

    public LocalDate getDate(Map<String, Object> params, String key) {
        return mapper.convertValue(params.get(key), LocalDate.class);
    }

    public List<UUID> getAccountIds(Map<String, Object> params) {
        return getIds(params, ACCOUNT_KEY);
    }

    public List<UUID> getCategoryIds(Map<String, Object> params) {
        return getIds(params, CATEGORY_KEY);
    }

    public List<UUID> getIds(Map<String, Object> params, String key) {
        try {
            return mapper.convertValue(
                    params.get(key), mapper.getTypeFactory().constructCollectionType(List.class, UUID.class));
        } catch (IllegalArgumentException exc) {
            throw new ValidationException(key + " has wrong type");
        }
    }

    public void validate(ReportType type, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            throw new ValidationException("Empty params");
        }
        List<ValidationError> errors = new ArrayList<>();
        errors.addAll(allFieldsPassed(params, getExpectedFields(type)));
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        errors.addAll(validateAccounts(params));
        if (!type.equals(ReportType.BALANCE)) {
            errors.addAll(checkDates(params));
            errors.addAll(validateCategories(params));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private Set<String> getExpectedFields(ReportType type) {
        Set<String> expectedFields = new HashSet<>();
        if (type.equals(ReportType.BALANCE)) {
            expectedFields.add(ACCOUNT_KEY);
        } else {
            expectedFields.addAll(List.of(ACCOUNT_KEY, FROM_KEY, TO_KEY, CATEGORY_KEY));
        }
        return expectedFields;
    }

    private List<ValidationError> allFieldsPassed(Map<String, Object> params, Set<String> expectedParams) {
        List<ValidationError> errors = new ArrayList<>();
        for (String key: params.keySet()) {
            if (!expectedParams.contains(key)) {
                errors.add(new ValidationError(key, "unexpected field"));
            }
        }
        for (String param: expectedParams) {
            ValidationError error = checkField(params, param);
            if (error != null) {
                errors.add(error);
            }
        }
        return errors;
    }

    private List<ValidationError> validateAccounts(Map<String, Object> params) {
        List<ValidationError> errors = new ArrayList<>();
        List<UUID> accountIds = getAccountIds(params);
        if (accountIds.isEmpty()) {
            errors.add(new ValidationError(ACCOUNT_KEY, "is empty"));
        }
        for (UUID accountId: accountIds) {
            Account account = communicator.getAccount(accountId);
            if (account == null) {
                errors.add(new ValidationError(ACCOUNT_KEY, String.format("account %s doesn't exist", accountId)));
            }
        }
        return errors;
    }

    public List<ValidationError> validateCategories(Map<String, Object> params) {
        List<ValidationError> errors = new ArrayList<>();
        List<UUID> categoryIds = getCategoryIds(params);
        if (categoryIds.isEmpty()) {
            errors.add(new ValidationError(CATEGORY_KEY, "is empty"));
        }
        for (UUID categoryId : categoryIds) {
            Category category = communicator.getCategory(categoryId);
            if (category == null) {
                errors.add(new ValidationError(CATEGORY_KEY, String.format("category %s doesn't exist", categoryId)));
            }
        }
        return errors;
    }

    private List<ValidationError> checkDates(Map<String, Object> params) {
        List<ValidationError> errors = new ArrayList<>();
        LocalDate from = getFrom(params);
        LocalDate to = getTo(params);
        if (from.isAfter(to)) {
            errors.add(new ValidationError("from", "date from is after date to"));
        }
        return errors;
    }

    private ValidationError checkField(Map<String, Object> params, String key) {
        final String fieldNotPassed = "field is missing";
        if (!params.containsKey(key) || params.get(key) == null) {
            return new ValidationError(key, fieldNotPassed);
        }
        return null;
    }


}
