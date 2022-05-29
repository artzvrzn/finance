package com.artzvrzn.view.api;

import com.artzvrzn.model.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;


public interface IOperationService {

    void create(UUID accountId, Operation dto);

    Page<Operation> get(UUID accountId, Pageable pageable);

    Page<Operation> get(UUID accountId, long from, long to, Collection<UUID> categories, Pageable pageable);

    void update(UUID accountId, UUID operationId, long updated, Operation dto);

    void delete(UUID accountId, UUID operationId, long updated);

}
