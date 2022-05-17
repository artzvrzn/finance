package com.artzvrzn.util.converter;

import com.artzvrzn.dao.api.entity.AccountEntity;
import com.artzvrzn.dao.api.entity.BalanceEntity;
import com.artzvrzn.model.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoToEntityConverter implements Converter<Account, AccountEntity> {

    @Override
    public AccountEntity convert(Account dto) {
        AccountEntity entity = new AccountEntity();
        entity.setId(dto.getId());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCurrency(dto.getCurrency());
        BalanceEntity balance = new BalanceEntity();
        balance.setAccount(entity);
        balance.setValue(dto.getBalance());
        entity.setBalance(balance);
        entity.setType(dto.getType());
        return entity;
    }
}
