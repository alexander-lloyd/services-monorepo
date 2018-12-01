package com.alexlloyd.configservice.model;

import java.util.Collections;
import java.util.Map;

public class Config {
    private Map<String, String> config;

    /**
     * Constructor.
     */
    public Config() {
        this.config = Collections.emptyMap();
    }

    /**
     * Constructor.
     * @param config create a Config from an existing Map.
     */
    public Config(Map<String, String> config) {
        this.config = config;
    }

    /**
     * Get the Config Map.
     * @return The config map.
     */
    public Map<String, String> getConfigMap() {
        return this.config;
    }

    /**
     * Add a key/value pair to a Config.
     * @param key the key.
     * @param value the value.
     */
    public void addConfig(String key, String value) {
        this.config.put(key, value);
    }

    /**
     * Given a key, get a value.
     * @param key The key.
     * @return The value.
     */
    public String getValue(String key) {
        return this.config.get(key);
    }
}
