package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Convert;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOperationRepository extends JpaRepository<OperationEntity, UUID> {

    Page<OperationEntity> findAllByAccount_Id(UUID id, Pageable pageable);

    Optional<OperationEntity> findByAccount_IdAndId(UUID accountId, UUID id);

    Page<OperationEntity> findAllByAccount_IdAndDateBetweenAndCategoryIn(
            UUID accountId, long from, long to, Collection<UUID> category, Pageable pageable);

    Page<OperationEntity> findAllByAccount_IdAndCategory(UUID accountId, UUID categoryId, Pageable pageable);
}
