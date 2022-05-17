package com.artzvrzn.view.api;

import com.artzvrzn.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAccountService {

    void create(Account account);

    Page<Account> get(Pageable pageable);

    Account get(UUID id);

    void update(UUID id, long updated, Account dto);
}
