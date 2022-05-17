package com.artzvrzn.view;

import com.artzvrzn.dao.api.IAccountRepository;
import com.artzvrzn.dao.api.IBalanceRepository;
import com.artzvrzn.dao.api.entity.AccountEntity;
import com.artzvrzn.exception.ValidationException;
import com.artzvrzn.model.Account;
import com.artzvrzn.util.TimeUtils;
import com.artzvrzn.util.Validation;
import com.artzvrzn.view.api.IAccountService;
import com.artzvrzn.view.api.IClassifierValidationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    private final IAccountRepository repository;
    private final IClassifierValidationService classifierService;
    private final ConversionService conversionService;

    public AccountService(IAccountRepository repository,
                          IBalanceRepository balanceRepository,
                          IClassifierValidationService classifierService,
                          ConversionService conversionService) {
        this.repository = repository;
        this.classifierService = classifierService;
        this.conversionService = conversionService;
    }

    @Override
    public void create(Account account) {
        Validation.validate(account);
        classifierService.isCurrencyPresent(account.getCurrency());
        long currentDateTime = TimeUtils.getCurrentSeconds();
        UUID id = UUID.randomUUID();
        account.setId(id);
        account.setCreated(currentDateTime);
        account.setUpdated(currentDateTime);
        AccountEntity entity = conversionService.convert(account, AccountEntity.class);
        repository.save(entity);
    }

    @Override
    public Page<Account> get(Pageable pageable) {
        return repository.findAll(pageable).map(e -> conversionService.convert(e, Account.class));
    }

    @Override
    public Account get(UUID id) {
        return conversionService.convert(getOrResolve(id), Account.class);
    }

    @Override
    public void update(UUID id, long updated, Account dto) {
        Validation.validate(dto);
        classifierService.isCurrencyPresent(dto.getCurrency());
        AccountEntity account = getOrResolve(id);
        if (account.getUpdated() != updated) {
            throw new ValidationException(String.format("Account %s has been already updated", id));
        }
        account.setTitle(dto.getTitle());
        account.setDescription(dto.getDescription());
        account.setType(dto.getType());
        account.setCurrency(dto.getCurrency());
        account.setUpdated(TimeUtils.getCurrentSeconds());
        repository.save(account);
    }

    private AccountEntity getOrResolve(UUID id) {
        Optional<AccountEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ValidationException(String.format("Account with id %s doesn't exist", id));
        }
        return optional.get();
    }
}
