package com.artzvrzn.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"uuid", "dt_created", "dt_updated", "title"})
public class Category extends BaseDTO {
}
