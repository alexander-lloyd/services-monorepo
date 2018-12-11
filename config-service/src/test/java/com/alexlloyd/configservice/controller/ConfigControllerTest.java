package com.alexlloyd.configservice.controller;

import java.util.Collections;

import com.alexlloyd.configservice.api.ConfigService;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.exception.ConfigDoesNotExistException;
import com.alexlloyd.configservice.model.Config;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigController.class)
class ConfigControllerTest {
    private static final String KEY = "Key";
    private static final String VALUE = "Value";
    private static final String CONFIG_NAME = "configName";
    private static final Config CONFIG = new Config(Collections.singletonMap(KEY, VALUE));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigService configService;

    @Test
    @DisplayName("should return a list of all the configs")
    void testGetConfigList() throws Exception {
        when(configService.getConfigNames()).thenReturn(Collections.singleton(CONFIG_NAME));

        mockMvc.perform(get("/config"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.response", is("SUCCESS")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.configs").isArray())
                .andExpect(jsonPath("$.data.configs", hasSize(1)))
                .andExpect(jsonPath("$.data.configs[0]", is(CONFIG_NAME)));
    }

    @Test
    @DisplayName("should return an empty list of configs when there are not any")
    void testGetConfigListEmptyList() throws Exception {
        when(configService.getConfigNames()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/config"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.response", is("SUCCESS")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.configs").isArray())
                .andExpect(jsonPath("$.data.configs", hasSize(0)));
    }

    @Test
    @DisplayName("should get a config")
    void testGetConfig() throws Exception {
        when(configService.getConfig(eq(CONFIG_NAME))).thenReturn(CONFIG);

        mockMvc.perform(get("/config/{configName}", CONFIG_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.response", is("SUCCESS")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.config").isMap())
                .andExpect(jsonPath("$.data.config." + KEY).value(is(VALUE)));
    }

    @Test
    @DisplayName("should return error if config does not exist")
    void testGetConfigException() throws Exception {
        when(configService.getConfig(eq(CONFIG_NAME))).thenThrow(new ConfigDoesNotExistException(CONFIG_NAME));

        mockMvc.perform(get("/config/{configName}", CONFIG_NAME))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.response", is("ERROR")))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].reason", containsString(CONFIG_NAME)))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("should create a config")
    void testCreateConfig() throws Exception {
        mockMvc.perform(post("/config/{configName}", CONFIG_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.response", is("SUCCESS")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data").isString())
                .andExpect(jsonPath("$.data", containsString(CONFIG_NAME)));


        verify(configService).createConfig(CONFIG_NAME);
    }

    @Test
    @DisplayName("should thrown an Exception if Config already exists")
    void testCreateConfigError() throws Exception {
        doThrow(new ConfigAlreadyExistsException(CONFIG_NAME)).when(configService).createConfig(eq(CONFIG_NAME));

        mockMvc.perform(post("/config/{configName}", CONFIG_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.response", is("FAILURE")))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].reason", containsString("exists")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("should update a config")
    void testUpdateConfig() throws Exception {
        mockMvc.perform(patch("/config/{configName}?key={key}&value={value}", CONFIG_NAME, KEY, VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.response", is("SUCCESS")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data", containsString("Update")));
    }
}