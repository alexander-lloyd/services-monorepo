package com.alexlloyd.configservice.service;

import java.util.Collection;
import java.util.Map;

import com.alexlloyd.configservice.api.ConfigDAO;
import com.alexlloyd.configservice.api.ConfigService;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.exception.ConfigDoesNotExistException;
import com.alexlloyd.configservice.exception.InvalidConfigNameException;
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
        if (configName == null) {
            throw new InvalidConfigNameException();
        }

        if (this.configDAO.hasConfig(configName)) {
            throw new ConfigAlreadyExistsException(configName);
        }

        this.configDAO.createConfig(configName);
    }

    /**
     * Get a Config object from storage.
     *
     * @param configName the name of the Config.
     * @return Config object if it exists or else null.
     * @throws ConfigDoesNotExistException if the config does not exist.
     */
    @Override
    public Config getConfig(String configName) throws ConfigDoesNotExistException {
        if (!this.configDAO.hasConfig(configName)) {
            throw new ConfigDoesNotExistException(configName);
        }

        Map<String, String> configMap = this.configDAO.getConfigMap(configName);

        return new Config(configMap);
    }

    /**
     * Update a Config with a key-value pair.
     *
     * @param configName the name of the Config.
     * @param key        the key to update.
     * @param value      the value.
     * @throws ConfigDoesNotExistException if the config does not exist.
     */
    @Override
    public void updateConfig(String configName, String key, String value) throws ConfigDoesNotExistException {
        if (!this.configDAO.hasConfig(configName)) {
            throw new ConfigDoesNotExistException(configName);
        }
        this.configDAO.updateConfig(configName, key, value);
    }

    /**
     * Delete a Config from storage.
     *
     * @param configName the name of the Config.
     * @throws ConfigDoesNotExistException if the config does not exist.
     */
    @Override
    public void deleteConfig(String configName) throws ConfigDoesNotExistException {
        if (!this.configDAO.hasConfig(configName)) {
            throw new ConfigDoesNotExistException(configName);
        }
        this.configDAO.deleteConfig(configName);
    }

    /**
     * Delete a value from a config.
     *
     * @param configName the name of the config.
     * @param key        the key to delete.
     * @throws ConfigDoesNotExistException if the config does not exist.
     */
    @Override
    public void deleteValue(String configName, String key) throws ConfigDoesNotExistException {
        if (!this.configDAO.hasConfig(configName)) {
            throw new ConfigDoesNotExistException(configName);
        }

        this.configDAO.deleteValue(configName, key);
    }

    /**
     * Get the name of all the config files.
     *
     * @return Collection of config names.
     */
    @Override
    public Collection<String> getConfigNames() {
        return this.configDAO.listConfigs();
    }
}
