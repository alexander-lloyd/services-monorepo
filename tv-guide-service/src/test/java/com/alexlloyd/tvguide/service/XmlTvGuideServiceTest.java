package com.alexlloyd.tvguide.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

import com.alexlloyd.tvguide.api.XmlTvGuideService;
import com.alexlloyd.tvguide.config.JacksonConfig;
import com.alexlloyd.tvguide.config.TVGuideBeans;
import com.alexlloyd.tvguide.models.Channel;
import com.alexlloyd.tvguide.models.GuideWrapper;
import com.alexlloyd.tvguide.models.Programme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
public class XmlTvGuideServiceTest {
    private static final String ID = "456";

    @Autowired
    public XmlTvGuideService xmlTvGuideService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGetTvGuide() {
        Programme programme = new Programme.Builder()
                .setChannelId("def")
                .setTitle("Programme Name")
                .setDescription("Programme Description")
                .build();
        Channel channel = new Channel.Builder()
                .setId("abc")
                .setName("Channel Name")
                .build();

        GuideWrapper wrapper = new GuideWrapper();
        wrapper.setProgrammes(Set.of(programme));
        wrapper.setChannels(Set.of(channel));

        doReturn(ResponseEntity.status(200).body(wrapper)).when(restTemplate).getForEntity(anyString(), any(), eq(ID));

        GuideWrapper guide = this.xmlTvGuideService.getGuide(ID);
        assertEquals(1, guide.getProgrammes().size());
        assertEquals(1, guide.getChannels().size());
    }

    @Configuration
    public static class ContextConfiguration {

        @Bean
        public RestTemplate restTemplate() {
            return mock(RestTemplate.class);
        }

        @Autowired
        @Bean
        public XmlTvGuideService xmlTvGuideService(RestTemplate restTemplate) {
            return new XmlTvGuideServiceImpl(restTemplate);
        }
    }
}
