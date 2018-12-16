package com.alexlloyd.configservice.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

public class Config {
    private final String configName;
    private final Map<String, String> configMap;

    /**
     * Constructor.
     */
    public Config(String configName) {
        this.configName = configName;
        this.configMap = new HashMap<>();
    }

    /**
     * Getter.
     *
     * @return The name of the config.
     */
    public String getConfigName() {
        return this.configName;
    }

    /**
     * Constructor.
     *
     * @param config create a Config from an existing Map.
     */
    public Config(String configName, Map<String, String> config) {
        this.configName = configName;
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
     * Add a key/value pair to a Config.
     *
     * @param key   the key.
     * @param value the value.
     */
    public void addConfig(String key, String value) {
        this.configMap.put(key, value);
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

    public void deleteKey(String key) {
        this.configMap.remove(key);
    }
}
