package com.artzvrzn.util.converter;

import com.artzvrzn.dao.api.entity.AccountEntity;
import com.artzvrzn.model.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityToDtoConverter implements Converter<AccountEntity, Account> {

    @Override
    public Account convert(AccountEntity entity) {
        Account dto = new Account();
        dto.setId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCurrency(entity.getCurrency());
        dto.setBalance(entity.getBalance().getValue());
        dto.setType(entity.getType());
        return dto;
    }
}
