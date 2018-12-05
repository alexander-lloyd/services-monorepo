package com.alexlloyd.configservice.api;

import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.model.Config;

public interface ConfigService {
    /**
     * Create a new Config
     *
     * @param configName the name of the Config.
     * @throws ConfigAlreadyExistsException if a Config with the same name already exists.
     */
    void createConfig(String configName) throws ConfigAlreadyExistsException;

    /**
     * Get a Config object from storage.
     *
     * @param configName the name of the Config.
     * @return Config object if it exists or else null.
     */
    Config getConfig(String configName);

    /**
     * Update a Config with a key-value pair.
     *
     * @param configName the name of the Config.
     * @param key        the key to update.
     * @param value      the value.
     */
    void updateConfig(String configName, String key, String value);

    /**
     * Delete a Config from storage.
     *
     * @param configName the name of the Config.
     */
    void deleteConfig(String configName);

    /**
     * Delete a value from a config.
     *
     * @param configName the name of the config.
     * @param key        the key to delete.
     */
    void deleteValue(String configName, String key);
}
