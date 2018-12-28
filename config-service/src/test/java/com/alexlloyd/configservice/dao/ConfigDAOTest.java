package com.alexlloyd.configservice.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.alexlloyd.configservice.api.ConfigDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
@SuppressWarnings("unchecked")
class ConfigDAOTest {
    private static final String DOMAIN_KEY = "config:";
    private static final String CONFIG_NAME = "configName";
    private static final String SECOND_CONFIG_NAME = "secondConfig";
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Autowired
    private ConfigDAO configDAO;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private HashOperations hashOperations;

    @Autowired
    private ZSetOperations zSetOperations;

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public ConfigDAO configDAO() {
            return new ConfigDAOImpl(redisTemplate());
        }

        @Bean
        public StringRedisTemplate redisTemplate() {
            StringRedisTemplate template = mock(StringRedisTemplate.class);

            when(template.opsForHash()).thenReturn(hashOperations());
            when(template.opsForZSet()).thenReturn(zSetOperations());

            return template;
        }

        @Bean
        public HashOperations hashOperations() {
            return mock(HashOperations.class);
        }

        @Bean
        public ZSetOperations zSetOperations() {
            return mock(ZSetOperations.class);
        }
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(redisTemplate, hashOperations, zSetOperations);
    }

    @DisplayName("should have a config")
    @Test
    public void testHasConfig() {
        when(zSetOperations.rank(any(), any())).thenReturn(1L);

        assertTrue(configDAO.hasConfig(CONFIG_NAME));

        verify(zSetOperations).rank(eq(DOMAIN_KEY), contains(CONFIG_NAME));
    }

    @DisplayName("should have a config returns false")
    @Test
    public void testHasConfigReturnsFalse() {
        when(zSetOperations.rank(eq(DOMAIN_KEY), contains(CONFIG_NAME))).thenReturn(-1L);

        assertFalse(configDAO.hasConfig(CONFIG_NAME));

        verify(zSetOperations).rank(eq(DOMAIN_KEY), contains(CONFIG_NAME));
    }

    @DisplayName("should have a config returns null")
    @Test
    public void testHasConfigReturnsNull() {
        when(zSetOperations.rank(any(), any())).thenReturn(null);

        assertFalse(configDAO.hasConfig(CONFIG_NAME));

        verify(zSetOperations).rank(eq(DOMAIN_KEY), contains(CONFIG_NAME));
    }

    @DisplayName("should be able to create a config")
    @Test
    public void testCreateConfig() {
        configDAO.createConfig(CONFIG_NAME);

        verify(hashOperations).putAll(contains(CONFIG_NAME), anyMap());
    }

    @DisplayName("should throw null pointer if configName is null")
    @Test()
    public void testCreateConfigNull() {
        assertThrows(NullPointerException.class, () -> configDAO.createConfig(null));
    }

    @DisplayName("should be able to delete a config")
    @Test
    public void testDeleteConfig() {
        configDAO.deleteConfig(CONFIG_NAME);

        verify(hashOperations).keys(contains(CONFIG_NAME));
        verify(zSetOperations).remove(eq(DOMAIN_KEY), contains(CONFIG_NAME));
    }

    @DisplayName("should get a config map")
    @Test
    public void testConfigMap() {
        when(hashOperations.entries(contains(CONFIG_NAME)))
                .thenReturn(Collections.singletonMap(KEY, VALUE));

        Map<String, String> configMap = configDAO.getConfigMap(CONFIG_NAME);
        assertEquals(1, configMap.size());

        assertEquals(VALUE, configMap.get(KEY));
    }

    @DisplayName("should return 0 when no configs in memory.")
    @Test
    public void testGetConfigCountNoConfigs() {
        when(redisTemplate.keys(anyString())).thenReturn(Collections.emptySet());

        assertEquals(0, configDAO.getConfigCount());
    }

    @DisplayName("should get the number of configs in storage")
    @Test
    public void testGetConfigCount() {
        when(zSetOperations.range(any(), anyLong(), anyLong())).thenReturn(Set.of(CONFIG_NAME));

        assertEquals(1, configDAO.getConfigCount());
    }

    @DisplayName("should delete all configs")
    @Test
    public void testDeleteAll() {
        when(zSetOperations.range(eq(DOMAIN_KEY), eq(0L), eq(-1L))).thenReturn(Set.of(CONFIG_NAME, SECOND_CONFIG_NAME));

        configDAO.deleteAll();

        verify(zSetOperations).range(eq(DOMAIN_KEY), eq(Long.MIN_VALUE), eq(Long.MAX_VALUE));

        verify(zSetOperations).range(eq(DOMAIN_KEY), eq(Long.MIN_VALUE), eq(Long.MAX_VALUE));
    }

    @DisplayName("should a collection of config names in storage.")
    @Test
    public void testListConfigs() {
        when(zSetOperations.range(eq(DOMAIN_KEY), eq(Long.MIN_VALUE), eq(Long.MAX_VALUE))).thenReturn(Set.of(CONFIG_NAME, SECOND_CONFIG_NAME));

        Collection<String> configs = configDAO.listConfigs();

        assertEquals(2, configs.size());
    }

    @DisplayName("should delete a value from the key store")
    @Test
    public void testDeleteValue() {
        configDAO.deleteValue(CONFIG_NAME, KEY);

        verify(hashOperations).delete(contains(CONFIG_NAME), eq(KEY));
    }
}