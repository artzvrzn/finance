package com.artzvrzn.controller.rest;

import com.artzvrzn.model.Operation;
import com.artzvrzn.view.api.IOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account/{uuid}/operation")
public class OperationController {

    private final IOperationService operationService;

    public OperationController(IOperationService operationService) {
        this.operationService = operationService;
    }

    @RequestMapping(value = {"", "/"},method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Operation> getOperations(@PathVariable UUID uuid,
                                         @RequestParam int page,
                                         @RequestParam int size) {
        PageRequest request = PageRequest.of(page, size);
        return operationService.get(uuid, request);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<?> createOperation(@PathVariable UUID uuid,
                                          @RequestBody Operation dto) {
        operationService.create(uuid, dto);
        return new ResponseEntity<>("Операция добавлена к счету", HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/{uuid_operation}/dt_update/{dt_update}"}, method = RequestMethod.PUT)
    public void updateOperation(@PathVariable("uuid") UUID accountId,
                                @PathVariable("uuid_operation") UUID operationId,
                                @PathVariable("dt_update") long updated,
                                @RequestBody Operation dto) {
        operationService.update(accountId, operationId, updated, dto);
    }

    @RequestMapping(value = {"/{uuid_operation}/dt_update/{dt_update}"}, method = RequestMethod.DELETE)
    public void deleteOperation(@PathVariable("uuid") UUID accountId,
                                @PathVariable("uuid_operation") UUID operationId,
                                @PathVariable("dt_update") long updated) {
        operationService.delete(accountId, operationId, updated);
    }
}
