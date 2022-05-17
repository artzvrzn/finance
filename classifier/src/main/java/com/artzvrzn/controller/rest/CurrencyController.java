package com.artzvrzn.controller.rest;

import com.artzvrzn.model.Currency;
import com.artzvrzn.view.api.IClassifierService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classifier/currency")
public class CurrencyController extends BaseController<Currency> {

    public CurrencyController(IClassifierService<Currency> classifierService) {
        super(classifierService);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Currency dto) {
        classifierService.create(dto);
    }
}
