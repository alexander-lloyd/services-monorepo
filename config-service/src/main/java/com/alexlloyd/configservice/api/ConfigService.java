package com.alexlloyd.configservice.api;

import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.model.Config;

public interface ConfigService {
    void createConfig(String configName) throws ConfigAlreadyExistsException;
    Config getConfig(String configName);
    void updateConfig(String configName, String key, String value);
    void deleteConfig(String configName);
}
