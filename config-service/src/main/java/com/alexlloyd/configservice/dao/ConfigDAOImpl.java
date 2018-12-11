package com.alexlloyd.configservice.dao;

import com.alexlloyd.configservice.api.ConfigDAO;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.model.Config;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Access Configs stored in Memory.
 */
@Repository
public class ConfigDAOImpl implements ConfigDAO {
    // TODO: Use persistent storage.
    private Map<String, Config> configMap = new HashMap<>();

    /**
     * Create a new Config inside the Storage.
     *
     * @param configName The name of the Config. Must be unique.
     * @throws ConfigAlreadyExistsException if a config with the same name already exists.
     * @throws NullPointerException         if configName is null.
     */
    public void createConfig(String configName) throws ConfigAlreadyExistsException {
        if (configName == null) {
            throw new NullPointerException("Config name is null");
        }

        if (this.configMap.containsKey(configName)) {
            throw new ConfigAlreadyExistsException(configName);
        }
        this.configMap.put(configName, new Config());
    }

    /**
     * Delete a config from the Storage.
     *
     * @param configName The name of the Config.
     */
    public void deleteConfig(String configName) {
        this.configMap.remove(configName);
    }

    /**
     * Get a Config object.
     *
     * @param configName The name of the Config.
     * @return The Config object.
     */
    public Config getConfig(String configName) {
        return this.configMap.get(configName);
    }

    /**
     * Get the number of configs in the Storage.
     *
     * @return the number of configs.
     */
    @Override
    public int getConfigCount() {
        return configMap.size();
    }

    /**
     * Delete all of the Config objects from the Map.
     */
    @Override
    public void deleteAll() {
        this.configMap.clear();
    }

    /**
     * Get all of the Config objects.
     *
     * @return Map of config name to Config object.
     */
    @Override
    public Map<String, Config> listConfigs() {
        return this.configMap;
    }
}
