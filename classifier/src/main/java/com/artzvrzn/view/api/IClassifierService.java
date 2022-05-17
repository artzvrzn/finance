package com.artzvrzn.view.api;

import com.artzvrzn.model.BaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IClassifierService<T extends BaseDTO> {

    void create(T dto);

    T get(UUID id);
    Page<T> get(Pageable pageable);

}
