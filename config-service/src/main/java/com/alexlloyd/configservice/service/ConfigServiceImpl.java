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
     * @param configDAO ConfigDAO implementation.
     */
    @Autowired
    public ConfigServiceImpl(ConfigDAO configDAO) {
        this.configDAO = configDAO;
    }

    /**
     * Create a Config.
     * @param configName the name of the config.
     * @throws ConfigAlreadyExistsException If the config already exists.
     */
    @Override
    public void createConfig(String configName) throws ConfigAlreadyExistsException {
        this.configDAO.createConfig(configName);
    }

    /**
     * Get a Config object.
     * @param configName the name of the config.
     * @return Config object.
     */
    @Override
    public Config getConfig(String configName) {
        return this.configDAO.getConfig(configName);
    }

    /**
     * Update a value in a config.
     * @param configName the name of the config.
     * @param key the key in the config.
     * @param value the value in the config.
     */
    @Override
    public void updateConfig(String configName, String key, String value) {
        Config config = this.getConfig(configName);
        config.addConfig(key, value);
    }

    /**
     * Delete a config.
     * @param configName the name of the config.
     */
    @Override
    public void deleteConfig(String configName) {
        this.configDAO.deleteConfig(configName);
    }
}
