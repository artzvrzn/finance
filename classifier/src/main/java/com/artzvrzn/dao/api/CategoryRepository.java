package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    @Query("SELECT c.id FROM Category c WHERE c.title = :title")
    UUID getIdByTitle(@Param("title") String title);
}
