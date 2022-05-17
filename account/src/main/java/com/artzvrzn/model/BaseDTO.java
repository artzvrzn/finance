package com.artzvrzn.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public abstract class BaseDTO {

    @JsonProperty(value = "uuid", access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @JsonProperty(value = "dt_created", access = JsonProperty.Access.READ_ONLY)
    private long created;
    @JsonProperty(value = "dt_updated", access = JsonProperty.Access.READ_ONLY)
    private long updated;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }
}
