package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOperationRepository extends JpaRepository<OperationEntity, UUID> {

    Page<OperationEntity> findAllByAccount_Id(UUID id, Pageable pageable);

    Optional<OperationEntity> findByAccount_IdAndId(UUID accountId, UUID id);

    Page<OperationEntity> findAllByAccount_IdAndDateBetween(
            UUID accountId, long from, long to, Pageable pageable);

    Page<OperationEntity> findAllByAccount_IdAndCategory(UUID accountId, UUID categoryId, Pageable pageable);
}
