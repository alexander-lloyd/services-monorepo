package com.alexlloyd.configservice.controller;

import java.util.Collection;

import com.alexlloyd.configservice.api.ConfigService;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.exception.ConfigDoesNotExistException;
import com.alexlloyd.configservice.model.Config;
import com.alexlloyd.configservice.model.DataWrapper;
import com.alexlloyd.configservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/config")
public class ConfigController {
    private ConfigService configService;

    /**
     * Constructor.
     *
     * @param configService Implementation of ConfigService.
     */
    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * Get the list of configs.
     *
     * @return Response containing list of configs.
     */
    @GetMapping
    public Response<DataWrapper<Collection<String>>> getConfigs() {
        Collection<String> configNames = this.configService.getConfigNames();

        return Response.success(new DataWrapper<>("configs", configNames));
    }

    /**
     * Get a Config object.
     *
     * @param configName the name of the config.
     * @return Config Response
     */
    @GetMapping(value = "/{configName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<DataWrapper<Config>> getConfig(@PathVariable("configName") String configName) throws ConfigDoesNotExistException {
        Config config = this.configService.getConfig(configName);

        return Response.success(new DataWrapper<>(config));
    }

    /**
     * Get a value from the Config object.
     *
     * @param configName the name of the config.
     * @param key        the key in the config.
     * @return the value in the config.
     */
    @GetMapping(value = "/{configName}/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> getValue(
            @PathVariable("configName") String configName,
            @PathVariable("key") String key) throws ConfigDoesNotExistException {

        String value = configService.getConfig(configName).getValue(key);

        return Response.success(value);
    }

    /**
     * Create a new Config.
     *
     * @param configName the name of the config.
     * @throws ConfigAlreadyExistsException if a config with the same name already exists.
     */
    @PostMapping(value = "/{configName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> createConfig(@PathVariable("configName") String configName) throws ConfigAlreadyExistsException {
        this.configService.createConfig(configName);

        return Response.success("Config " + configName + " created");
    }

    /**
     * Update a config with a key and value.
     *
     * @param configName the name of the config.
     * @param key        the key.
     * @param value      the value.
     */
    @PatchMapping("/{configName}")
    public Response<String> updateConfig(
            @PathVariable("configName") String configName,
            @RequestParam("key") String key,
            @RequestParam("value") String value) throws ConfigDoesNotExistException {
        this.configService.updateConfig(configName, key, value);

        return Response.success("Update config " + configName);
    }

    /**
     * Delete a config.
     *
     * @param configName the name of the config.
     */
    @DeleteMapping("/{configName}")
    public void deleteConfig(
            @PathVariable("configName") String configName) throws ConfigDoesNotExistException {
        this.configService.deleteConfig(configName);
    }

    /**
     * Delete a value from the config.
     *
     * @param configName the name of the config.
     * @param key        the key to delete.
     */
    @DeleteMapping("/{configName}/{key}")
    public void deleteValue(
            @PathVariable("configName") String configName,
            @PathVariable("key") String key) throws ConfigDoesNotExistException {
        this.configService.deleteValue(configName, key);
    }

    /**
     * Exception handler for {@link ConfigAlreadyExistsException}
     *
     * @param exception instance of the exception
     * @return Failure response back to the user with the exception.
     */
    @ExceptionHandler(value = ConfigAlreadyExistsException.class)
    public Response handleConfigAlreadyExistsException(ConfigAlreadyExistsException exception) {
        return Response.failure(exception);
    }

    /**
     * Exception handler for {@link ConfigDoesNotExistException}
     *
     * @param exception instance of the exception
     * @return Error response back to the user with the exception.
     */
    @ExceptionHandler(value = ConfigDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleConfigDoesNotExistException(ConfigDoesNotExistException exception) {
        return Response.error(exception);
    }
}
