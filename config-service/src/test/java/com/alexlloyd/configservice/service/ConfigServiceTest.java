package com.alexlloyd.configservice.service;

import com.alexlloyd.configservice.api.ConfigDAO;
import com.alexlloyd.configservice.api.ConfigService;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
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
    private static final Config CONFIG = new Config();
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public ConfigDAO configDAO() {
            ConfigDAO configDAO = mock(ConfigDAO.class);

            when(configDAO.getConfig(eq(CONFIG_NAME))).thenReturn(CONFIG);

            return configDAO;
        }

        @Bean
        public ConfigService configService() {
            return new ConfigServiceImpl(configDAO());
        }
    }

    @Autowired
    private ConfigService configService;

    @Test
    @DisplayName("should be able to create and delete configs")
    void testCreateConfig() throws ConfigAlreadyExistsException {
        configService.createConfig(CONFIG_NAME);

        Config config = configService.getConfig(CONFIG_NAME);
        assertEquals(config, CONFIG);

        configService.deleteConfig(CONFIG_NAME);
    }

    @Test
    @DisplayName("Test updating a configs")
    void testUpdateConfig() {
        Config config = configService.getConfig(CONFIG_NAME);

        configService.updateConfig(CONFIG_NAME, KEY, VALUE);

        assertEquals(config.getValue(KEY), VALUE);

        String value = configService.getConfig(CONFIG_NAME).getValue(KEY);

        assertEquals(value, VALUE);
    }
}