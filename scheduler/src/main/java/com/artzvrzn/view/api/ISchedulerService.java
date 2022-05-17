package com.artzvrzn.view.api;

import com.artzvrzn.model.Operation;
import com.artzvrzn.model.Schedule;
import com.artzvrzn.model.ScheduledOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ISchedulerService {

    void create(ScheduledOperation scheduledOperation);

    Page<ScheduledOperation> get(Pageable pageable);

    ScheduledOperation get(UUID id);

    void update(UUID id, long updated, ScheduledOperation scheduledOperation);

    void delete(UUID id);

}
