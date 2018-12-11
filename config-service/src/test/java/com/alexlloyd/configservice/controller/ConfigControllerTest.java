package com.alexlloyd.configservice.controller;

import java.util.Collections;

import com.alexlloyd.configservice.api.ConfigService;
import com.alexlloyd.configservice.exception.ConfigAlreadyExistsException;
import com.alexlloyd.configservice.model.Config;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andExpect(jsonPath("$.data.config."+KEY).value(is(VALUE)));
    }

    @Test
    @DisplayName("should create a config")
    void testCreateConfig() throws Exception {
        mockMvc.perform(post("/config/" + CONFIG_NAME))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        verify(configService).createConfig(CONFIG_NAME);
    }

    @Test
    @DisplayName("should thrown an Exception if Config already exists")
    void testCreateConfigError() throws Exception {
        doThrow(ConfigAlreadyExistsException.class).when(configService).createConfig(eq(CONFIG_NAME));

        mockMvc.perform(post("/config/" + CONFIG_NAME))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}