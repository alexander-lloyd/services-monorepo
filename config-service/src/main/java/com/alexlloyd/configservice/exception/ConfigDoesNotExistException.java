package com.alexlloyd.configservice.exception;

import com.alexlloyd.response.model.ApplicationException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * Exception thrown when getting a config that does not exist.
 */
@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
public class ConfigDoesNotExistException extends ApplicationException {
    private final String reason;

    public ConfigDoesNotExistException(String configName) {
        reason = "Config " + configName + " does not exist";
    }

    @JsonProperty("reason")
    public String getReason() {
        return this.reason;
    }
}
