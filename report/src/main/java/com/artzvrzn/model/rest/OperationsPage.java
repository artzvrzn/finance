package com.artzvrzn.model.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class OperationsPage extends PageImpl<Operation> {

    @JsonCreator
    public OperationsPage(@JsonProperty("content") List<Operation> content,
                          @JsonProperty("number") int number,
                          @JsonProperty("size") int size,
                          @JsonProperty("total_pages") long total) {
        super(content, PageRequest.of(number, size), total);
    }
}
