package com.alexlloyd.configservice.controller;

import com.alexlloyd.configservice.ConfigServiceApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConfigServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class FaviconTest {
    private static final String FAVICON_URL = "/favicon.ico";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should not have a favicon")
    void testNoFavicon() throws Exception {

        mockMvc.perform(get(FAVICON_URL))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
