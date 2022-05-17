package com.artzvrzn.controller.rest;

import com.artzvrzn.model.Category;
import com.artzvrzn.view.api.IClassifierService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classifier/operation/category")
public class CategoryController extends BaseController<Category> {

    public CategoryController(IClassifierService<Category> classifierService) {
        super(classifierService);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Category dto) {
        classifierService.create(dto);
    }

}
