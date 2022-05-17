package com.artzvrzn.controller.rest;

import com.artzvrzn.model.Account;
import com.artzvrzn.view.api.IAccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = {"", "/"},
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Account> getAccounts(@RequestParam(name = "page") int page,
                                     @RequestParam(name = "size") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return accountService.get(pageRequest);
    }

    @RequestMapping(value = {"/{uuid}", "/{uuid}/"}, method = RequestMethod.GET)
    public Account getAccount(@PathVariable UUID uuid) {
        return accountService.get(uuid);
    }

    @RequestMapping(value = {"", "/"},
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createAccount(@RequestBody Account account) {
        accountService.create(account);
    }

    @RequestMapping(value = {"/{uuid}/dt_update/{dt_update}", "/{uuid}/dt_update/{dt_update}/"},
                    method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccount(@PathVariable("uuid") UUID uuid,
                              @PathVariable("dt_update") long updated,
                              @RequestBody Account account) {
        accountService.update(uuid, updated, account);
    }

}
