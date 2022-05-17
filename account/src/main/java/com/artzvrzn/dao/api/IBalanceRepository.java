package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface IBalanceRepository extends JpaRepository<BalanceEntity, UUID> {
    @Modifying
    @Query(value = "UPDATE finance.balances AS b\n" +
            "SET value = o.sum_value\n" +
            "FROM (SELECT SUM(value) AS sum_value, account_id \n" +
            "\t  FROM finance.operations \n" +
            "\t  GROUP BY account_id) AS o\n" +
            "WHERE b.account_id = o.account_id", nativeQuery = true)
    @Transactional
    void updateBalance();

}
