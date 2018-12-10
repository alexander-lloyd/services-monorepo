package com.alexlloyd.configservice.api;

import java.util.Map;

import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.model.Config;

/**
 * Access Configs from the Storage.
 */
public interface ConfigDAO {
    /**
     * Create a new Config inside the Storage.
     *
     * @param configName The name of the Config. Must be unique.
     * @throws ConfigAlreadyExistsException if a config with the same name already exists.
     */
    void createConfig(String configName) throws ConfigAlreadyExistsException;

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
    Config getConfig(String configName);

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
    Map<String, Config> listConfigs();
}
