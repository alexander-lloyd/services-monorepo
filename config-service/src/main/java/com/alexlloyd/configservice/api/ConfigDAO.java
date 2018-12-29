package com.alexlloyd.configservice.api;

import java.util.Collection;
import java.util.Map;

/**
 * Access Configs from the Storage.
 */
public interface ConfigDAO {
    boolean hasConfig(String configName);

    /**
     * Create a new Config inside the Storage.
     *
     * @param configName The name of the Config. Must be unique.
     */
    void createConfig(String configName);

    /**
     * Delete a config from the Storage.
     *
     * @param configName The name of the Config.
     */
    void deleteConfig(String configName);

    /**
     * Get a Config object.
     *
     * @param configName The name of the Config.
     * @return The Config object.
     */
    Map<String, String> getConfigMap(String configName);

    /**
     * Get the number of configs in the Storage.
     *
     * @return the number of configs.
     */
    int getConfigCount();

    /**
     * Delete all of the Config objects from the Map.
     */
    void deleteAll();

    /**
     * Get all of the Config objects.
     *
     * @return Map of config name to Config object.
     */
    Collection<String> listConfigs();

    /**
     * Delete a value from a config.
     *
     * @param configName The name of the config.
     * @param key The key to delete.
     */
    void deleteValue(String configName, String key);

    /**
     * Update a value from the config.
     *
     * @param configName The name of the config.
     * @param key The key to update.
     * @param value The new value.
     */
    void updateConfig(String configName, String key, String value);
}
