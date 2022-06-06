package com.artzvrzn.mail.scheduler.controller;

import com.artzvrzn.mail.scheduler.model.ScheduledMail;
import com.artzvrzn.mail.scheduler.view.api.IMailSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/scheduler/report")
public class ScheduleController {

    @Autowired
    private IMailSchedulerService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = {"/", ""}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody ScheduledMail scheduledMail) {
        service.schedule(scheduledMail);
    }

    @GetMapping(value = {"/{uuid}", "/{uuid}/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduledMail get(@PathVariable("uuid") UUID id) {
        return service.get(id);
    }

    @GetMapping(value = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ScheduledMail> get(@RequestParam("page") int page,
                                   @RequestParam("size") int size) {
        PageRequest request = PageRequest.of(page, size);
        return service.get(request);
    }

    @PutMapping(value = {"/{uuid}/dt_update/{dt_update}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody ScheduledMail scheduledMail,
                                    @PathVariable("uuid") UUID id,
                                    @PathVariable("dt_update") long updated) {
        service.update(id, updated, scheduledMail);
    }
}
