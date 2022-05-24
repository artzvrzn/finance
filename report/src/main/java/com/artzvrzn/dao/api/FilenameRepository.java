package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.FilenameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface FilenameRepository extends JpaRepository<FilenameEntity, UUID> {

    @Transactional
    @Modifying
    @Query(value = "update app.reports_path set path = :filename where report_id = :id", nativeQuery = true)
    void updateFilename(@Param("id") UUID id, @Param("filename") String filename);
}
