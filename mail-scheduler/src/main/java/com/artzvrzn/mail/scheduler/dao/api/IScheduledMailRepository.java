package com.artzvrzn.mail.scheduler.dao.api;

import com.artzvrzn.mail.scheduler.dao.api.entity.ScheduledMailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IScheduledMailRepository extends JpaRepository<ScheduledMailEntity, UUID> {

}
