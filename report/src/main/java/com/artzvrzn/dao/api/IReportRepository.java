package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.ReportEntity;
import com.artzvrzn.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface IReportRepository extends JpaRepository<ReportEntity, UUID> {

    @Transactional
    @Modifying
    @Query(value = "update app.reports " +
            "set status = :#{#status?.ordinal()}, updated = :dt_update " +
            "where id = :id", nativeQuery = true)
    void updateStatus(@Param("id") UUID id, @Param("status") Status status, @Param("dt_update") LocalDateTime update);

    @Transactional
    @Query(value = "select * from app.reports where id = :id and status = :#{#status?.ordinal()}", nativeQuery = true)
    ReportEntity findByIdIfStatus(@Param("id") UUID id, @Param("status") Status status);

}
