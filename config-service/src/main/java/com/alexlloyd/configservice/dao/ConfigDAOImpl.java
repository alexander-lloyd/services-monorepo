package com.alexlloyd.configservice.dao;

import com.alexlloyd.configservice.api.ConfigDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Access Configs stored in Memory.
 */
@Repository
public class ConfigDAOImpl implements ConfigDAO {
    private static final String DOMAIN_KEY = "config:";

    private final ZSetOperations<String, String> zSetOps;
    private final HashOperations<String, Object, Object> hashOps;

    /**
     * Constructor.
     *
     * @param redisTemplate The Redis Template.
     */
    @Autowired
    public ConfigDAOImpl(StringRedisTemplate redisTemplate) {
        this.zSetOps = redisTemplate.opsForZSet();
        this.hashOps = redisTemplate.opsForHash();
    }

    /**
     * Build the redis key.
     *
     * @param configName the name of the config.
     * @return the Redis key as a byte array.
     */
    private String buildKey(String configName) {
        return DOMAIN_KEY + configName;
    }

    private long getStringScore(String key) {
        String string = key.toUpperCase();

        return LongStream.rangeClosed(0, string.length() - 1L)
                .reduce(0, (acc, value) ->
                        acc + (string.charAt((int)value) * (long)Math.pow(10, value)));
    }

    /**
     * Does a config exist in storage?
     *
     * @param configName the name of the config.
     * @return returns true if the key exists in the config. Else otherwise.
     */
    @Override
    public boolean hasConfig(String configName) {
        Long hasKey = this.zSetOps.rank(DOMAIN_KEY, configName);

        return hasKey != null && hasKey > -1;
    }

    /**
     * Create a new Config inside the Storage.
     *
     * @param configName The name of the Config. Must be unique.
     */
    @Override
    public void createConfig(String configName) {
        if (configName == null) {
            throw new NullPointerException("configName is null");
        }

        String redisKey = buildKey(configName);

        this.zSetOps.add(DOMAIN_KEY, configName, getStringScore(configName));
        this.hashOps.putAll(redisKey, Map.of("\0", "\1"));
    }

    /**
     * Delete a config from the Storage.
     *
     * @param configName The name of the Config.
     */
    @Override
    public void deleteConfig(String configName) {
        String redisKey = buildKey(configName);

        Set<Object> hashKeys = this.hashOps.keys(buildKey(configName));

        // If there are any keys in the hash, delete them.
        if (!hashKeys.isEmpty()) {
            this.hashOps.delete(redisKey, hashKeys.toArray());
        }

        this.zSetOps.remove(DOMAIN_KEY, configName);
    }

    /**
     * Get a Config object.
     *
     * @param configName The name of the Config.
     * @return The Config object.
     */
    @Override
    public Map<String, String> getConfigMap(String configName) {

        return this.hashOps.entries(buildKey(configName))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals("\0"))
                .collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue()));
    }

    /**
     * Get the number of configs in the Storage.
     *
     * @return the number of configs.
     */
    @Override
    public int getConfigCount() {
        return this.listConfigs().size();
    }

    /**
     * Delete all of the Config objects from the Map.
     */
    @Override
    public void deleteAll() {
        this.listConfigs()
                .forEach(this::deleteConfig);
    }

    /**
     * Get all of the Config objects.
     *
     * @return Map of config name to Config object.
     */
    @Override
    public Collection<String> listConfigs() {
        return this.zSetOps.range(DOMAIN_KEY, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Override
    public void deleteValue(String configName, String key) {
        this.hashOps.delete(buildKey(configName), key);
    }

    @Override
    public void updateConfig(String configName, String key, String value) {
        this.hashOps.put(buildKey(configName), key, value);
    }
}
