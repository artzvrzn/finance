package com.artzvrzn.controller.rest;

import com.artzvrzn.model.BaseDTO;
import com.artzvrzn.view.api.IClassifierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public abstract class BaseController<T extends BaseDTO> {

    protected final IClassifierService<T> classifierService;

    protected BaseController(IClassifierService<T> classifierService) {
        this.classifierService = classifierService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public abstract void create(@RequestBody T dto);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<T> get(@RequestParam int page, @RequestParam int size) {
        PageRequest request = PageRequest.of(page, size);
        return classifierService.get(request);
    }

    @RequestMapping(value = {"/{uuid}", "/{uuid}/"},
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public T get(@PathVariable("uuid") UUID uuid) {
        return classifierService.get(uuid);
    }


}
