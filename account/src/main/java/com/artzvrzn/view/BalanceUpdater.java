package com.artzvrzn.view;

import com.artzvrzn.dao.api.IBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BalanceUpdater {

    @Autowired
    private IBalanceRepository balanceRepository;

    @Scheduled(fixedRate = 30000)
    public void update() {
        balanceRepository.updateBalance();
    }
}
