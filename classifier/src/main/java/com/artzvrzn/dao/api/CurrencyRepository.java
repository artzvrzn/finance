package com.artzvrzn.dao.api;

import com.artzvrzn.dao.api.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, UUID> {

    @Query("SELECT c.id FROM Currency c WHERE c.title = :title")
    UUID getIdByTitle(@Param("title") String title);
}
