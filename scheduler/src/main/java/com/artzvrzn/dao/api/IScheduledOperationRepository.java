package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.ScheduledOperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IScheduledOperationRepository extends JpaRepository<ScheduledOperationEntity, UUID> {
}