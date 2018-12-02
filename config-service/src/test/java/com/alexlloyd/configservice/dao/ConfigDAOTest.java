package com.alexlloyd.configservice.dao;

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
        public ConfigDAO storeDAO() {
            return new ConfigDAOImpl();
        }
    }

    @Autowired
    private ConfigDAO storeDAO;

    @BeforeEach
    void beforeEach() {
        assertEquals(0, storeDAO.getConfigCount());
    }

    @AfterEach
    void afterEach() {
        // Ensure the DAO is clear after each test
        storeDAO.deleteAll();
    }


    @Test
    @DisplayName("should be able to create a store")
    void testCreateStore() throws ConfigAlreadyExistsException {
        assertNull(storeDAO.getConfig(CONFIG_NAME));

        storeDAO.createConfig(CONFIG_NAME);
        Config config = storeDAO.getConfig(CONFIG_NAME);

        assertNotNull(config);
    }

    @Test
    @DisplayName("should be able to delete a store")
    void testDeleteStore() throws ConfigAlreadyExistsException {
        storeDAO.createConfig(CONFIG_NAME);
        Config config = storeDAO.getConfig(CONFIG_NAME);
        assertNotNull(config);

        storeDAO.deleteConfig(CONFIG_NAME);

        assertNull(storeDAO.getConfig(CONFIG_NAME));
    }

    @Test
    @DisplayName("should throw Exception if the store already exists")
    void testCreateAlreadyExistingStore() throws ConfigAlreadyExistsException {
        storeDAO.createConfig(CONFIG_NAME);

        assertThrows(ConfigAlreadyExistsException.class, () -> storeDAO.createConfig(CONFIG_NAME));
    }

    @Test
    @DisplayName("should throw NullPointerException if null configName")
    void testNullConfigName() {
        assertThrows(NullPointerException.class, () -> storeDAO.createConfig(null));
    }
}