package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.FilePropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface IFilePropsRepository extends JpaRepository<FilePropertyEntity, UUID> {

    @Transactional
    @Modifying
    @Query(value = "update app.file_properties set path = :path, extension = :ext where report_id = :id", nativeQuery = true)
    void updateFilename(@Param("id") UUID id, @Param("path") String path, @Param("ext") String extension);

    @Query(value = "select * from app.file_properties where report_id = :id", nativeQuery = true)
    FilePropertyEntity getByReportId(@Param("id") UUID id);
}
