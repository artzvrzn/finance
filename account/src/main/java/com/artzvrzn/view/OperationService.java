package com.artzvrzn.view;

import com.artzvrzn.dao.api.IAccountRepository;
import com.artzvrzn.dao.api.IOperationRepository;
import com.artzvrzn.dao.api.entity.AccountEntity;
import com.artzvrzn.dao.api.entity.OperationEntity;
import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.Operation;
import com.artzvrzn.util.TimeUtils;
import com.artzvrzn.util.Validation;
import com.artzvrzn.view.api.IClassifierValidationService;
import com.artzvrzn.view.api.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OperationService implements IOperationService {

    @Autowired
    private IOperationRepository operationRepository;
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IClassifierValidationService classifierService;
    @Autowired
    private ConversionService conversionService;

    @Override
    public void create(UUID accountId, Operation operation) {
        Optional<AccountEntity> optional = accountRepository.findById(accountId);
        if (optional.isEmpty()) {
            throw new ValidationException(String.format("User with id %s not found", accountId));
        }
        validateOperation(operation);
        operation.setId(UUID.randomUUID());
        long created = TimeUtils.getCurrentSeconds();
        operation.setCreated(created);
        operation.setUpdated(created);
        OperationEntity operationEntity = conversionService.convert(operation, OperationEntity.class);
        AccountEntity accountEntity = optional.get();
        if (!accountEntity.getCurrency().equals(operationEntity.getCurrency())) {
            throw new ValidationException("This operation is forbidden for this account due to another currency type");
        }
        operationEntity.setAccount(accountEntity);
        operationRepository.save(operationEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Operation> get(UUID accountId, Pageable pageable) {
        return operationRepository
                .findAllByAccount_Id(accountId, pageable)
                .map(e -> conversionService.convert(e, Operation.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Operation> get(UUID accountId, long from, long to, Collection<UUID> categories, Pageable pageable) {
        return operationRepository
                .findAllByAccount_IdAndDateBetweenAndCategoryIn(accountId, from, to, categories, pageable)
                .map(e -> conversionService.convert(e, Operation.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Operation> get(UUID accountId, UUID categoryId, Pageable pageable) {
        return operationRepository
                .findAllByAccount_IdAndCategory(accountId, categoryId, pageable)
                .map(e -> conversionService.convert(e, Operation.class));
    }

    @Override
    public void update(UUID accountId, UUID operationId, long updated, Operation operation) {
        validateOperation(operation);
        OperationEntity entity = getOrResolve(accountId, operationId);
//        Optional<AccountEntity> optional = accountRepository.findById(accountId);
//        if (optional.isEmpty()) {
//            throw new ValidationException(String.format("User with id %s not found", accountId));
//        }
        if (entity.getUpdated() != updated) {
            throw new ValidationException(String.format("Operation %s has been already updated", operationId));
        }
        entity.setUpdated(TimeUtils.getCurrentSeconds());
        entity.setCurrency(operation.getCurrency());
        entity.setCategory(operation.getCategory());
        entity.setDescription(operation.getDescription());
        entity.setDate(operation.getDate());
        entity.setValue(operation.getValue());
//        double newSum = entity.getAccount().getBalance().getValue() + operation.getValue();
//        entity.getAccount().getBalance().setValue(newSum);
        operationRepository.save(entity);
    }

    @Override
    public void delete(UUID accountId, UUID operationId, long updated) {
        OperationEntity entity = getOrResolve(accountId, operationId);
        if (entity.getUpdated() != updated) {
            throw new ValidationException(String.format("Operation %s has been already updated", operationId));
        }
        operationRepository.delete(entity);
    }

    private OperationEntity getOrResolve(UUID accountId, UUID operationId) {
        Optional<OperationEntity> optional = operationRepository.findByAccount_IdAndId(accountId, operationId);
        if (optional.isEmpty()) {
            throw new ValidationException(
                    String.format("No such operation (account %s, operation %s)", accountId, operationId));
        }
        return optional.get();
    }

    private void validateOperation(Operation operation) {
        Validation.validate(operation);
        classifierService.isCurrencyPresent(operation.getCurrency());
        classifierService.isCategoryPresent(operation.getCategory());
    }

}
