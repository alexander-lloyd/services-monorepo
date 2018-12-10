package com.alexlloyd.configservice.dao;

import java.util.Map;

import com.alexlloyd.configservice.api.ConfigDAO;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.model.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
class ConfigDAOTest {
    private static final String CONFIG_NAME = "configName";

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public ConfigDAO configDAO() {
            return new ConfigDAOImpl();
        }
    }

    @Autowired
    private ConfigDAO configDAO;

    @BeforeEach
    void beforeEach() {
        assertEquals(0, configDAO.getConfigCount());
    }

    @AfterEach
    void afterEach() {
        // Ensure the DAO is clear after each test
        configDAO.deleteAll();
    }


    @Test
    @DisplayName("should be able to create a config")
    void testCreateConfig() throws ConfigAlreadyExistsException {
        assertNull(configDAO.getConfig(CONFIG_NAME));

        configDAO.createConfig(CONFIG_NAME);
        Config config = configDAO.getConfig(CONFIG_NAME);

        assertNotNull(config);
    }

    @Test
    @DisplayName("should be able to delete a config")
    void testDeleteConfig() throws ConfigAlreadyExistsException {
        configDAO.createConfig(CONFIG_NAME);
        Config config = configDAO.getConfig(CONFIG_NAME);
        assertNotNull(config);

        configDAO.deleteConfig(CONFIG_NAME);

        assertNull(configDAO.getConfig(CONFIG_NAME));
    }

    @Test
    @DisplayName("should throw Exception if the config already exists")
    void testCreateAlreadyExistingConfig() throws ConfigAlreadyExistsException {
        configDAO.createConfig(CONFIG_NAME);

        assertThrows(ConfigAlreadyExistsException.class, () -> configDAO.createConfig(CONFIG_NAME));
    }

    @Test
    @DisplayName("should throw NullPointerException if null configName")
    void testNullConfigName() {
        assertThrows(NullPointerException.class, () -> configDAO.createConfig(null));
    }

    @Test
    @DisplayName("should return all configs")
    void testListConfigs() throws ConfigAlreadyExistsException {
        Map<String, Config> configs = configDAO.listConfigs();

        assertEquals(0, configs.size());

        configDAO.createConfig(CONFIG_NAME);

        configs = configDAO.listConfigs();

        assertEquals(1, configs.size());
    }
}