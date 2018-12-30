package com.alexlloyd.configservice.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = NONE, setterVisibility = NONE)
public class Config {
    private final Map<String, String> configMap;

    /**
     * Constructor.
     *
     * @param config create a Config from an existing Map.
     */
    public Config(Map<String, String> config) {
        this.configMap = config;
    }

    /**
     * Get the Config Map.
     *
     * @return The config map.
     */
    @JsonAnyGetter
    public Map<String, String> getConfigMap() {
        return this.configMap;
    }

    /**
     * Given a key, get a value.
     *
     * @param key The key.
     * @return The value.
     */
    public String getValue(String key) {
        return this.configMap.get(key);
    }
}
