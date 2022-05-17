package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAccountRepository extends JpaRepository<AccountEntity, UUID> {

    @EntityGraph(attributePaths = {"balance"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<AccountEntity> findAll(Pageable pageable);
}
