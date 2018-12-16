package com.alexlloyd.configservice.service;

import java.util.Collection;
import java.util.Collections;

import com.alexlloyd.configservice.api.ConfigDAO;
import com.alexlloyd.configservice.api.ConfigService;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.exception.ConfigDoesNotExistException;
import com.alexlloyd.configservice.model.Config;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
class ConfigServiceTest {
    private static final String CONFIG_NAME = "configName";
    private static final Config CONFIG = new Config(CONFIG_NAME);
    private static final String KEY = "key";
    private static final String VALUE = "value";

    private static final String NON_EXISTENT_CONFIG = "non-existent-config";

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public ConfigDAO configDAO() throws ConfigDoesNotExistException {
            ConfigDAO configDAO = mock(ConfigDAO.class);

            when(configDAO.getConfig(eq(CONFIG_NAME))).thenReturn(CONFIG);

            when(configDAO.listConfigs()).thenReturn(Collections.singletonMap(CONFIG_NAME, CONFIG));

            doThrow(ConfigDoesNotExistException.class).when(configDAO).getConfig(eq(NON_EXISTENT_CONFIG));

            return configDAO;
        }

        @Bean
        public ConfigService configService() throws ConfigDoesNotExistException {
            return new ConfigServiceImpl(configDAO());
        }
    }

    @Autowired
    private ConfigService configService;

    @Test
    @DisplayName("should be able to create and delete configs")
    void testCreateConfig() throws ConfigAlreadyExistsException, ConfigDoesNotExistException {
        configService.createConfig(CONFIG_NAME);

        Config config = configService.getConfig(CONFIG_NAME);
        assertEquals(config, CONFIG);

        configService.deleteConfig(CONFIG_NAME);
    }

    @Test
    @DisplayName("should update a config")
    void testUpdateConfig() throws ConfigDoesNotExistException {
        Config config = configService.getConfig(CONFIG_NAME);

        configService.updateConfig(CONFIG_NAME, KEY, VALUE);

        assertEquals(config.getValue(KEY), VALUE);

        String value = configService.getConfig(CONFIG_NAME).getValue(KEY);

        assertEquals(value, VALUE);
    }

    @Test
    @DisplayName("should delete a value from a config")
    void testDeleteConfig() throws ConfigDoesNotExistException {
        assertEquals(VALUE, CONFIG.getValue(KEY));

        configService.deleteValue(CONFIG_NAME, KEY);

        assertNull(CONFIG.getValue(KEY));
    }

    @Test
    @DisplayName("should throw exception when trying to delete value from non-existent config")
    void testDeleteConfigException() {

        assertThrows(ConfigDoesNotExistException.class, () -> configService.deleteValue(NON_EXISTENT_CONFIG, KEY));
    }

    @Test
    @DisplayName("should get all of the config names")
    void testGetConfigNames() {
        Collection<String> configNames = configService.getConfigNames();

        assertEquals(1, configNames.size());
        assertTrue(configNames.contains(CONFIG_NAME));
    }
}