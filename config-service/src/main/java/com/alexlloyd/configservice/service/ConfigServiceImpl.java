package com.alexlloyd.configservice.service;

import com.alexlloyd.configservice.api.ConfigDAO;
import com.alexlloyd.configservice.api.ConfigService;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.model.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {
    private ConfigDAO configDAO;

    /**
     * Constructor.
     *
     * @param configDAO ConfigDAO implementation.
     */
    @Autowired
    public ConfigServiceImpl(ConfigDAO configDAO) {
        this.configDAO = configDAO;
    }

    /**
     * Create a new Config
     *
     * @param configName the name of the Config.
     * @throws ConfigAlreadyExistsException if a Config with the same name already exists.
     */
    @Override
    public void createConfig(String configName) throws ConfigAlreadyExistsException {
        this.configDAO.createConfig(configName);
    }

    /**
     * Get a Config object from storage.
     *
     * @param configName the name of the Config.
     * @return Config object if it exists or else null.
     */
    @Override
    public Config getConfig(String configName) {
        return this.configDAO.getConfig(configName);
    }

    /**
     * Update a Config with a key-value pair.
     *
     * @param configName the name of the Config.
     * @param key        the key to update.
     * @param value      the value.
     */
    @Override
    public void updateConfig(String configName, String key, String value) {
        Config config = this.getConfig(configName);
        config.addConfig(key, value);
    }

    /**
     * Delete a Config from storage.
     *
     * @param configName the name of the Config.
     */
    @Override
    public void deleteConfig(String configName) {
        this.configDAO.deleteConfig(configName);
    }

    /**
     * Delete a value from a config.
     *
     * @param configName the name of the config.
     * @param key        the key to delete.
     */
    @Override
    public void deleteValue(String configName, String key) {
        this.getConfig(configName).deleteKey(key);
    }
}
