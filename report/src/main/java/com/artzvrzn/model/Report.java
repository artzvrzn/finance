package com.artzvrzn.model;

import com.artzvrzn.serializer.LocalDateTimeToLongSerializer;
import com.artzvrzn.serializer.LongToLocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonPropertyOrder({"uuid", "dt_create", "dt_update", "status", "type", "description", "params"})
public class Report {

    @JsonProperty(value = "uuid", access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @JsonProperty(value = "dt_create", access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    @JsonDeserialize(using = LongToLocalDateTimeDeserializer.class)
    private LocalDateTime created;
    @JsonProperty(value = "dt_update", access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = LocalDateTimeToLongSerializer.class)
    @JsonDeserialize(using = LongToLocalDateTimeDeserializer.class)
    private LocalDateTime updated;
    private Status status;
    private ReportType type;
    private String description;
    @JsonIgnore
    private String filename;
    @JsonPropertyOrder(alphabetic = true)
    private Map<String, Object> params;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
