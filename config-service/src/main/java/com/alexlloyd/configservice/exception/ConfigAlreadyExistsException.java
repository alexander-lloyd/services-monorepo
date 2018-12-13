package com.alexlloyd.configservice.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * Exception thrown if a configName has already been used.
 */
@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
public class ConfigAlreadyExistsException extends Exception {
    private final String reason;

    /**
     * Constructor.
     *
     * @param configName The name of the existing config.
     */
    public ConfigAlreadyExistsException(String configName) {
        this.reason = "Config " + configName + "already exists";
    }

    /**
     * Get the reason why this exception was thrown.
     *
     * @return Human readable string why exception was raised.
     */
    @JsonProperty(value = "reason")
    public String getReason() {
        return reason;
    }
}
