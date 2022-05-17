package com.artzvrzn.view;

import com.artzvrzn.dao.api.entity.BaseEntity;
import com.artzvrzn.model.BaseDTO;
import com.artzvrzn.view.api.IClassifierService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public abstract class ClassifierService<T extends BaseDTO> implements IClassifierService<T> {
    
    protected <E extends BaseEntity> E generateBaseFields(E entity) {
        long created = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        entity.setCreated(created);
        entity.setUpdated(created);
        entity.setId(UUID.randomUUID());
        return entity;
    }
}
