package com.artzvrzn.controller.rest;

import com.artzvrzn.model.ScheduledOperation;
import com.artzvrzn.view.api.ISchedulerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/scheduler/operation")
public class ScheduleController {

    private final ISchedulerService schedulerService;

    public ScheduleController(ISchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping(value = {"/", ""}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOperation(@RequestBody ScheduledOperation operation) {
        schedulerService.create(operation);
    }

    @GetMapping(value = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ScheduledOperation> getOperations(@RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size) {
        PageRequest request = PageRequest.of(page, size);
        return schedulerService.get(request);
    }

    @PutMapping(value = {"/{uuid}/dt_update/{dt_update}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateOperation(@PathVariable(name = "uuid") UUID uuid,
                                @PathVariable(name = "dt_update") long update,
                                @RequestBody ScheduledOperation operation) {
        schedulerService.update(uuid, update, operation);
    }

    @DeleteMapping(value = {"/{uuid}", "/{uuid}/"})
    public void deleteOperation(@PathVariable(name = "uuid") UUID uuid) {
        schedulerService.delete(uuid);
    }
}