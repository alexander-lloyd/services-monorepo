package com.alexlloyd.tvguide.controller;

import java.util.List;

import com.alexlloyd.tvguide.api.TvGuideService;
import com.alexlloyd.tvguide.model.Channel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TVChannelController.class)
public class TVChannelControllerTest {
    private static final String CHANNEL_ID = "id";
    private static final String CHANNEL_NAME = "name";
    private static final Channel CHANNEL = new Channel.Builder()
            .setChannelId(CHANNEL_ID)
            .setName(CHANNEL_NAME)
            .build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TvGuideService tvGuideService;

    @DisplayName("should get a list of channels")
    @Test
    public void testGetChannelList() throws Exception {
        when(tvGuideService.getChannels()).thenReturn(List.of(CHANNEL));

        mockMvc.perform(get("/tv/channel"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.response", is("SUCCESS")))
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.channels").isArray())
                .andExpect(jsonPath("$.data.channels", hasSize(1)))
                .andExpect(jsonPath("$.data.channels[0].channelId", is(CHANNEL_ID)))
                .andExpect(jsonPath("$.data.channels[0].name", is(CHANNEL_NAME)))
                .andExpect(jsonPath("$.data.channels[0].icon", nullValue()));
    }
}
