package com.alexlloyd.configservice.service;

import java.util.Collection;
import java.util.Collections;

import com.alexlloyd.configservice.api.ConfigDAO;
import com.alexlloyd.configservice.api.ConfigService;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.exception.ConfigDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
class ConfigServiceTest {
    private static final String CONFIG_NAME = "configName";
    private static final String KEY = "key";
    private static final String VALUE = "value";

    private static final String NOT_EXISTENT_CONFIG = "non-existent-config";
    private static final String ALREADY_EXISTS_CONFIG = "config-exists";

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public ConfigDAO configDAO() {
            ConfigDAO configDAO = mock(ConfigDAO.class);

            when(configDAO.getConfigMap(eq(CONFIG_NAME))).thenReturn(Collections.singletonMap(KEY, VALUE));

            when(configDAO.hasConfig(any())).thenReturn(false);
            when(configDAO.hasConfig(eq(ALREADY_EXISTS_CONFIG))).thenReturn(true);

            when(configDAO.listConfigs()).thenReturn(Collections.singleton(CONFIG_NAME));

            return configDAO;
        }

        @Bean
        public ConfigService configService() {
            return new ConfigServiceImpl(configDAO());
        }
    }

    @Autowired
    private ConfigDAO configDAO;

    @Autowired
    private ConfigService configService;

    @BeforeEach
    public void beforeEach() {
        Mockito.clearInvocations(configDAO);
    }

    @DisplayName("should thrown null pointer if configName is null")
    @Test
    public void testCreateConfigNull() {
        assertThrows(NullPointerException.class, () -> configService.createConfig(null));
    }

    @DisplayName("should be able to create and delete configs")
    @Test
    public void testCreateConfig() throws ConfigAlreadyExistsException {
        configService.createConfig(CONFIG_NAME);

        verify(configDAO).createConfig(CONFIG_NAME);
    }

    @DisplayName("should thrown an exception if trying to create a config that already exists")
    @Test
    public void testCreateConfigAlreadyExists() {
        assertThrows(ConfigAlreadyExistsException.class, () -> configService.createConfig(ALREADY_EXISTS_CONFIG));
    }

    @DisplayName("should get a config")
    @Test
    public void testGetConfig() throws ConfigDoesNotExistException {
        configService.getConfig(ALREADY_EXISTS_CONFIG);

        verify(configDAO).getConfigMap(ALREADY_EXISTS_CONFIG);
    }

    @DisplayName("should throw ConfigDoesNotExist if a non-existent config called.")
    @Test
    public void testGetConfigNotExist() {
        assertThrows(ConfigDoesNotExistException.class, () -> configService.getConfig(NOT_EXISTENT_CONFIG));
    }

    @DisplayName("should update a config")
    @Test
    public void testUpdateConfig() throws ConfigDoesNotExistException {
        configService.updateConfig(ALREADY_EXISTS_CONFIG, KEY, VALUE);
        verify(configDAO).updateConfig(eq(ALREADY_EXISTS_CONFIG), eq(KEY), eq(VALUE));
    }


    @DisplayName("should thrown exception trying to update non existent config")
    @Test
    public void testUpdateConfigNotExist() {
        assertThrows(ConfigDoesNotExistException.class, () -> configService.updateConfig(NOT_EXISTENT_CONFIG, KEY, VALUE));
    }

    @DisplayName("should delete a config")
    @Test
    public void testDeleteConfig() throws ConfigDoesNotExistException {
        configService.deleteConfig(ALREADY_EXISTS_CONFIG);
        verify(configDAO).deleteConfig(eq(ALREADY_EXISTS_CONFIG));
    }

    @DisplayName("should thrown exception if trying to delete config that does not exist")
    @Test
    public void testDeleteConfigException() {
        assertThrows(ConfigDoesNotExistException.class, () -> configService.deleteConfig(NOT_EXISTENT_CONFIG));
    }

    @DisplayName("should delete value from config")
    @Test
    public void testDeleteValue() throws ConfigDoesNotExistException {
        configService.deleteValue(ALREADY_EXISTS_CONFIG, KEY);

        verify(configDAO).deleteValue(eq(ALREADY_EXISTS_CONFIG), eq(KEY));
    }

    @DisplayName("should throw exception if trying to delete value from config that does not exist")
    @Test
    public void testDeleteValueException() {
        assertThrows(ConfigDoesNotExistException.class, () ->configService.deleteValue(NOT_EXISTENT_CONFIG, KEY));
    }

    @DisplayName("should get all of the config names")
    @Test
    public void testGetConfigNames() {
        Collection<String> configNames = configService.getConfigNames();

        assertEquals(1, configNames.size());
        assertTrue(configNames.contains(CONFIG_NAME));

        verify(configDAO).listConfigs();
    }
}